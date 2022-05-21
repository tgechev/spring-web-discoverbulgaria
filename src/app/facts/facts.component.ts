import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { AppService } from '../app.service';
import { RegionService } from '../region.service';
import { FactService } from '../fact.service';
import { ImageService } from '../image.service';
import { Region } from '../interfaces/Region';
import { Fact } from '../interfaces/Fact';
import {
  FileItem,
  FileUploader,
  FileUploaderOptions,
  ParsedResponseHeaders,
} from 'ng2-file-upload';
import { Cloudinary } from '@cloudinary/angular-5.x';
import * as $ from 'jquery';
import { chooseFactTitle, cloudinaryBaseUrl, Type } from "../../constants";

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
    private imageService: ImageService,
    private cloudinary: Cloudinary,
    private http: HttpClient,
    private router: Router,
  ) {}

  regions: Region[] = [];
  facts: Fact[] = [];
  isEdit = false;
  factSaved = false;
  factDeleted = false;

  currentFact: Fact = this.initFact();

  uploader!: FileUploader;

  private uploadedImageResponse?: any;

  initFact(): Fact {
    return {
      title: '',
      description: '',
      readMore: '',
      regionId: '',
      type: Type.HISTORY,
    };
  }

  ngOnInit(): void {
    this.app.toggleMainBackground();
    if (this.router.url.endsWith('edit')) {
      this.isEdit = true;
    }

    const uploaderOptions: FileUploaderOptions =
      this.imageService.getImageUploadOptions();

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
    this.factService.getAllFacts().subscribe(facts => {
      this.facts = [];
      const dummyFact = this.initFact();
      dummyFact.title = chooseFactTitle;
      this.facts.push(dummyFact);
      this.facts.push(...facts);
    });
  }

  assignSelectedFact(event: Fact): void {
    if (event.title === chooseFactTitle) {
      if (this.currentFact.regionId !== '') {
        this.currentFact = this.initFact();
      }
      return;
    }
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

  deleteImage(data: any): void {
    this.imageService
      .deleteImage(this.uploadedImageResponse)
      .subscribe((response: any) => {
        console.log(`Deleted image - ${data.public_id} ${response.result}`);
      });
  }

  onSubmit(): void {
    if (this.currentFact.regionId === '') {
      return;
    }
    if (this.currentFact.imageUrl?.startsWith('https')) {
      this.currentFact.imageUrl = this.currentFact.imageUrl?.substring(
        cloudinaryBaseUrl.length,
      );
    }
    if (this.isEdit) {
      this.factService.editFact(this.currentFact).subscribe({
        next: response => {
          if (response.status == 200) {
            console.log(`success edit fact`);
            this.uploadedImageResponse = null;
            this.factSaved = true;
            this.getFacts();
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
            this.factSaved = true;
            this.currentFact = this.initFact();
          }
        },
        error: (error: HttpErrorResponse) => {
          console.log('An Error Occurred ' + error.message);
        },
      });
    }
  }

  dismissAlert(): void {
    this.factSaved = this.factSaved ? !this.factSaved : this.factSaved;
    this.factDeleted = this.factDeleted ? !this.factDeleted : this.factDeleted;
  }

  deleteSelectedFact(): void {
    if (!this.isEdit) {
      return;
    }

    this.factService.deleteFactById(this.currentFact.id as string).subscribe({
      next: response => {
        if (response.status === 200 && response.body?.deleted) {
          this.factDeleted = true;
          this.currentFact = this.initFact();
          this.getFacts();
        }
      },
      error: (error: HttpErrorResponse) => {
        console.log('An Error Occurred ' + error.message);
      },
    });
  }
}
