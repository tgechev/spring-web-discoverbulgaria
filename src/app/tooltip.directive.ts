import { Directive, HostListener, Renderer2 } from '@angular/core';

@Directive({
  selector: '[tooltip]'
})
export class TooltipDirective {
  tooltip: HTMLElement | null = null;
  private lastRegionId: string = '';

  constructor(private renderer: Renderer2) { }

  @HostListener('mousemove', ['$event']) onMouseMove(event: MouseEvent) {
    const target: HTMLElement = event.target as HTMLElement;
    if (target.id.includes('BG')) {
      if (!this.tooltip) {
        this.tooltip = document.getElementById(`tooltip-${target.id}`);
      }
      if (this.lastRegionId !== target.id) {
        this.hide();
        this.lastRegionId = target.id;
        this.tooltip = document.getElementById(`tooltip-${target.id}`);
      } else {
        this.show(event.clientX, event.clientY);
      }
    } else if (this.tooltip) {
      this.hide();
    }
  }

  @HostListener('mouseleave') onMouseLeave() {
    if (this.tooltip) {
      this.hide();
    }
  }

  show(mouseX: number, mouseY: number): void {
    this.renderer.removeClass(this.tooltip, 'd-none');
    this.renderer.setStyle(this.tooltip, 'position', `fixed`);
    this.renderer.setStyle(this.tooltip, 'z-index', 9999);
    this.renderer.setStyle(this.tooltip, 'top', `${mouseY - 170}px`);
    this.renderer.setStyle(this.tooltip, 'left', `${mouseX + 10}px`);
  }

  hide(): void {
    this.renderer.addClass(this.tooltip, 'd-none');
    this.renderer.removeStyle(this.tooltip, 'position');
    this.renderer.removeStyle(this.tooltip, 'top');
    this.renderer.removeStyle(this.tooltip, 'left');
    this.tooltip = null;
  }
}
