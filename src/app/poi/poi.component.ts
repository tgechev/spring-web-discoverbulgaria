import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { RegionService } from '../region.service';
import { Region } from '../interfaces/Region';
import { Poi } from '../interfaces/Poi';
import { PoiService } from '../poi.service';
import { Router } from '@angular/router';
import { Fact } from '../interfaces/Fact';
import {
  FileItem,
  FileUploader,
  FileUploaderOptions,
  ParsedResponseHeaders,
} from 'ng2-file-upload';
import {
  choosePoiTitle,
  cloudinaryBaseUrl,
  cloudinaryPresets,
  Type,
} from '../../constants';
import * as $ from 'jquery';
import { ImageService } from '../image.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-poi',
  templateUrl: './poi.component.html',
  styleUrls: ['./poi.component.css'],
})
export class PoiComponent implements OnInit, AfterViewInit, OnDestroy {
  constructor(
    private app: AppService,
    private regionService: RegionService,
    private poiService: PoiService,
    private router: Router,
    private imageService: ImageService,
  ) {}

  regions: Region[] = [];
  poi: Poi[] = [];
  poiSaved = false;
  poiDeleted = false;

  currentPoi: Poi = this.initPoi();

  uploader!: FileUploader;

  private uploadedImageResponse?: any;

  initPoi(): Poi {
    return {
      title: '',
      description: '',
      readMore: '',
      regionId: '',
      type: Type.HISTORY,
      address: '',
      longitude: 0,
      latitude: 0,
      videoId: '',
    };
  }

  ngOnInit(): void {
    this.app.toggleMainBackground();

    const uploaderOptions: FileUploaderOptions =
      this.imageService.getImageUploadOptions();

    this.uploader = new FileUploader(uploaderOptions);

    this.uploader.onBuildItemForm = (
      fileItem: FileItem,
      form: FormData,
    ): any => {
      // Add Cloudinary's unsigned upload preset to the upload form
      form.append('upload_preset', cloudinaryPresets.poi);
      form.append('callback', '/cloudinary/cloudinary_cors.html');
      // Add file to upload
      form.append('file', fileItem as any);
      // Use default "withCredentials" value for CORS requests
      fileItem.withCredentials = false;
      return { fileItem, form };
    };
    this.uploader.onBeforeUploadItem = (item: FileItem) => {
      if (this.uploadedImageResponse) {
        this.deleteImage();
      }
      this.app.toggleAddEditImageLoader();
    };

    // Update model on completion of uploading a file
    this.uploader.onCompleteItem = (
      item: FileItem,
      response: string,
      status: number,
      headers: ParsedResponseHeaders,
    ) => {
      this.app.toggleAddEditImageLoader(item.file.name);
      this.uploadedImageResponse = JSON.parse(response);
      this.currentPoi.imageUrl =
        this.uploadedImageResponse.secure_url.substring(
          cloudinaryBaseUrl.length,
        );
    };
  }

  ngAfterViewInit(): void {
    this.getRegions();
    if (this.isEdit) {
      this.getPoi();
    }
  }

  ngOnDestroy(): void {
    if (this.uploadedImageResponse) {
      this.deleteImage();
    }
  }

  assignSelectedPoi(event: Fact): void {
    if (event.title === choosePoiTitle) {
      if (this.currentPoi.regionId !== '') {
        this.currentPoi = this.initPoi();
      }
      return;
    }
    Object.assign(this.currentPoi, event);
  }

  public toggleLabels(): void {
    this.app.toggleLabels();
  }

  deleteImage(): void {
    this.imageService.deleteImage(this.uploadedImageResponse).subscribe();
  }

  getRegions(): void {
    this.regionService
      .getRegions()
      .subscribe(regions => (this.regions = regions));
  }

  getPoi(): void {
    this.poiService.getAllPoi().subscribe(poi => {
      this.poi = [];
      const dummyPoi = this.initPoi();
      dummyPoi.title = choosePoiTitle;
      this.poi.push(dummyPoi);
      this.poi.push(...poi);
    });
  }

  get isEdit() {
    return this.router.url.endsWith('edit');
  }

  dismissAlert(): void {
    this.poiSaved = this.poiSaved ? !this.poiSaved : this.poiSaved;
    this.poiDeleted = this.poiDeleted ? !this.poiDeleted : this.poiDeleted;
  }

  deleteSelectedPoi(): void {
    if (!this.isEdit) {
      return;
    }

    this.poiService.deletePoiById(this.currentPoi.id as string).subscribe({
      next: response => {
        if (response.status === 200 && response.body?.deleted) {
          this.poiDeleted = true;
          this.currentPoi = this.initPoi();
          this.getPoi();
          this.toggleLabels();
          window.scroll(0, 0);
        }
      },
      error: (error: HttpErrorResponse) => {
        console.log('An Error Occurred ' + error.message);
      },
    });
  }

  onSubmit(): void {
    if (this.currentPoi.regionId === '') {
      return;
    }
    if (this.currentPoi.imageUrl?.startsWith('https')) {
      this.currentPoi.imageUrl = this.currentPoi.imageUrl?.substring(
        cloudinaryBaseUrl.length,
      );
    }
    $('input.file-path').attr('value', '');
    if (this.isEdit) {
      this.poiService.editPoi(this.currentPoi).subscribe({
        next: response => {
          if (response.status == 200) {
            console.log(`success edit fact`);
            this.uploadedImageResponse = null;
            this.poiSaved = true;
            this.getPoi();
            window.scroll(0, 0);
          }
        },
        error: (error: HttpErrorResponse) => {
          console.log('An Error Occurred ' + error.message);
        },
      });
    } else {
      this.poiService.addPoi(this.currentPoi).subscribe({
        next: response => {
          if (response.status == 200) {
            console.log(`success add fact`);
            this.uploadedImageResponse = null;
            this.poiSaved = true;
            this.currentPoi = this.initPoi();
            this.toggleLabels();
            window.scroll(0, 0);
          }
        },
        error: (error: HttpErrorResponse) => {
          console.log('An Error Occurred ' + error.message);
        },
      });
    }
  }
}
