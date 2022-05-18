import { Directive, HostListener } from '@angular/core';
import * as $ from 'jquery';

@Directive({
  selector: '[active-label]',
})
export class ActiveLabelDirective {
  constructor() {}

  @HostListener('focus', ['$event'])
  public onFocus(event: any): void {
    const target = event.target as HTMLElement;
    const label = $(`label[for=${target.id}]`);
    if (!label.hasClass('active')) {
      label.addClass('active');
    }
  }

  @HostListener('blur', ['$event'])
  public onBlur(event: any): void {
    const target = event.target as HTMLElement;
    if (!$(target).val()) {
      $(`label[for=${target.id}]`).removeClass('active');
    }
  }
}
