import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Cloudinary } from '@cloudinary/angular-5.x';
import { Observable } from 'rxjs';
import { FileUploaderOptions } from 'ng2-file-upload';

@Injectable({
  providedIn: 'root',
})
export class ImageService {
  constructor(private cloudinary: Cloudinary, private http: HttpClient) {}

  // Delete an uploaded image
  // Requires setting "Return delete token" to "Yes" in your upload preset configuration
  // See also https://support.cloudinary.com/hc/en-us/articles/202521132-How-to-delete-an-image-from-the-client-side-
  deleteImage(data: any): Observable<any> {
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
    return this.http.post<HttpResponse<any>>(url, body, httpOptions);
  }

  getImageUploadOptions(): FileUploaderOptions {
    return {
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
  }
}
