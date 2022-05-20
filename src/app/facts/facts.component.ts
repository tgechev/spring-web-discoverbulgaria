import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { RegionService } from '../region.service';
import { Region } from '../interfaces/Region';
import {
  FileItem,
  FileUploader,
  FileUploaderOptions,
  ParsedResponseHeaders,
} from 'ng2-file-upload';
import { Cloudinary } from '@cloudinary/angular-5.x';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import * as $ from 'jquery';
import { Fact } from '../interfaces/Fact';
import { cloudinaryBaseUrl, Type } from "../../constants";
import { Router } from '@angular/router';
import { FactService } from '../fact.service';

@Component({
  selector: 'app-facts',
  templateUrl: './facts.component.html',
  styleUrls: ['./facts.component.css'],
})
export class FactsComponent implements OnInit, AfterViewInit, OnDestroy {
  constructor(
    private app: AppService,
    private regionService: RegionService,
    private factService: FactService,
    private cloudinary: Cloudinary,
    private http: HttpClient,
    private router: Router,
  ) {}

  regions: Region[] = [];
  facts: Fact[] = [];
  isEdit = false;

  currentFact: Fact = {
    title: '',
    description: '',
    readMore: '',
    regionId: '',
    type: Type.HISTORY,
  };

  uploader!: FileUploader;

  private uploadedImageResponse?: any;

  ngOnInit(): void {
    this.app.toggleMainBackground();
    if (this.router.url.endsWith('edit')) {
      this.isEdit = true;
    }

    // Create the file uploader, wire it to upload to your account
    const uploaderOptions: FileUploaderOptions = {
      url: `https://api.cloudinary.com/v1_1/${
        this.cloudinary.config().cloud_name
      }/upload`,
      // Upload files automatically upon addition to upload queue
      autoUpload: true,
      // Use xhrTransport in favor of iframeTransport
      isHTML5: true,
      // Calculate progress independently for each uploaded file
      removeAfterUpload: true,
      // XHR request headers
      headers: [
        {
          name: 'X-Requested-With',
          value: 'XMLHttpRequest',
        },
      ],
    };
    this.uploader = new FileUploader(uploaderOptions);

    this.uploader.onBuildItemForm = (
      fileItem: FileItem,
      form: FormData,
    ): any => {
      // Add Cloudinary's unsigned upload preset to the upload form
      form.append('upload_preset', 'v6pqytxh');
      form.append('callback', '/cloudinary/cloudinary_cors.html');
      // Add file to upload
      form.append('file', fileItem as any);
      // Use default "withCredentials" value for CORS requests
      fileItem.withCredentials = false;
      return { fileItem, form };
    };
    this.uploader.onBeforeUploadItem = (item: FileItem) => {
      if (this.uploadedImageResponse) {
        this.deleteImage(this.uploadedImageResponse);
      }
      const loader = $('#loader');
      loader.removeClass('d-none');
      loader.addClass('d-flex');
      loader.css({ 'padding-top': 0 });
      $('.file-path-wrapper').addClass('d-none');
    };

    // Update model on completion of uploading a file
    this.uploader.onCompleteItem = (
      item: FileItem,
      response: string,
      status: number,
      headers: ParsedResponseHeaders,
    ) => {
      const loader = $('#loader');
      loader.removeClass('d-flex');
      loader.addClass('d-none');
      $('.file-path-wrapper').removeClass('d-none');
      $('input.file-path').attr('value', item.file.name);
      this.uploadedImageResponse = JSON.parse(response);
      this.currentFact.imageUrl =
        this.uploadedImageResponse.secure_url.substring(
          cloudinaryBaseUrl.length,
        );
    };
  }

  ngAfterViewInit(): void {
    this.getRegions();
    if (this.router.url.endsWith('edit')) {
      this.getFacts();
    }
  }

  ngOnDestroy(): void {
    if (this.uploadedImageResponse) {
      this.deleteImage(this.uploadedImageResponse);
    }
  }

  getRegions(): void {
    this.regionService
      .getRegions()
      .subscribe(regions => (this.regions = regions));
  }

  getFacts(): void {
    this.factService.getAllFacts().subscribe(facts => (this.facts = facts));
  }

  assignSelectedFact(event: Region): void {
    Object.assign(this.currentFact, event);
  }

  onSelectFact(): void {
    this.setActiveLabels($('input'));
    this.setActiveLabels($('textarea'));
  }

  setActiveLabels(el: JQuery): void {
    el.each(function () {
      if ($(this).val()) {
        $(`label[for=${this.id}]`).addClass('active');
      }
    });
  }

  // Delete an uploaded image
  // Requires setting "Return delete token" to "Yes" in your upload preset configuration
  // See also https://support.cloudinary.com/hc/en-us/articles/202521132-How-to-delete-an-image-from-the-client-side-
  deleteImage(data: any): void {
    const url = `https://api.cloudinary.com/v1_1/${
      this.cloudinary.config().cloud_name
    }/delete_by_token`;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'X-Requested-With': 'XMLHttpRequest',
      }),
    };
    const body = {
      token: data.delete_token,
    };
    this.http.post(url, body, httpOptions).subscribe((response: any) => {
      console.log(`Deleted image - ${data.public_id} ${response.result}`);
    });
  }

  onSubmit(): void {
    if (this.currentFact.imageUrl?.startsWith('https')) {
      this.currentFact.imageUrl = this.currentFact.imageUrl?.substring(
        cloudinaryBaseUrl.length,
      );
    }
    if (this.isEdit) {
      this.factService.editFact(this.currentFact).subscribe({
        next: response => {
          if (response.status == 200) {
            console.log(`success add fact`);
            this.uploadedImageResponse = null;
          }
        },
        error: (error: HttpErrorResponse) => {
          console.log('An Error Occurred ' + error.message);
        },
      });
    } else {
      this.factService.addFact(this.currentFact).subscribe({
        next: response => {
          if (response.status == 200) {
            console.log(`success add fact`);
            this.uploadedImageResponse = null;
          }
        },
        error: (error: HttpErrorResponse) => {
          console.log('An Error Occurred ' + error.message);
        },
      });
    }
  }
}
