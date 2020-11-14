import {Component, OnInit} from '@angular/core';
import {OverlayService} from './overlay.service';

@Component({
  selector: 'app-overlay',
  templateUrl: './overlay.component.html',
  styleUrls: ['./overlay.component.css']
})
export class OverlayComponent implements OnInit {

  visible = false;
  message = '';

  constructor(private overlayService: OverlayService) {
  }

  ngOnInit() {
    this.overlayService.showOverlay$.subscribe(next => {
      this.show(next);
    });
  }

  onClose() {
    /*this.overlayService.hideOverlay$.subscribe(next => {
      this.hide();
    });*/
    this.hide();
  }

  private show(message) {
    console.log('-- Overlay would be shown!!!');
    this.message = message;
    this.visible = true;
  }

  private hide() {
    this.visible = false;
  }
}
