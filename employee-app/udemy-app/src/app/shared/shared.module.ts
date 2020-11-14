import {NgModule} from '@angular/core';
import {OverlayComponent} from './overlay/overlay.component';
import {CommonModule} from '@angular/common';

@NgModule({
  imports: [CommonModule],
  declarations: [OverlayComponent],
  exports: [OverlayComponent]
})
export class SharedModule {
}
