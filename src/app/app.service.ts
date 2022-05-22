import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as $ from 'jquery';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  constructor(private http: HttpClient, private router: Router) {}

  public toggleMainBackground(): void {
    const mainEl = $('.main');
    if (!this.router.url.endsWith('home') && !this.router.url.endsWith('all')) {
      if (mainEl.hasClass('main-raised')) {
        mainEl.removeClass('main-raised');
        mainEl.removeClass('text-center');
        mainEl.css({ background: 'rgba(0,0,0,0)' });
      }
    } else if (!mainEl.hasClass('main-raised')) {
      mainEl.addClass('main-raised');
      mainEl.addClass('text-center');
      mainEl.css({ background: '' });
    }
  }

  public toggleAddEditImageLoader(itemName?: string): void {
    const loader = $('#loader');
    if (loader.hasClass('d-none')) {
      loader.removeClass('d-none');
      loader.addClass('d-flex');
      loader.css({ 'padding-top': 0 });
      $('.file-path-wrapper').addClass('d-none');
    } else if (itemName) {
      loader.removeClass('d-flex');
      loader.addClass('d-none');
      $('.file-path-wrapper').removeClass('d-none');
      $('input.file-path').attr('value', itemName);
    }
  }

  public toggleLabels(addOrDelete?: boolean): void {
    this.toggleActiveClass($('input.form-control'), addOrDelete);
    this.toggleActiveClass($('textarea.form-control'), addOrDelete);
  }

  private toggleActiveClass(el: JQuery, addOrDelete?: boolean): void {
    el.each(function () {
      const label = $(`label[for=${this.id}]`);
      const currentEl = $(this);
      if (currentEl.val() && currentEl.val() !== '' && !addOrDelete) {
        label.addClass('active');
      } else {
        label.removeClass('active');
      }
    });
  }
}
