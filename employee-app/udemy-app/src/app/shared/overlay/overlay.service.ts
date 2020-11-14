import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable({providedIn: 'root'})
export class OverlayService {

  showOverlay$: Subject<any>;
  hideOverlay$: Subject<any>;

  constructor() {
    this.showOverlay$ = new Subject();
    this.hideOverlay$ = new Subject();
  }

  showOverlay(message) {
    console.log('-- show overlay!!');
    this.showOverlay$.next(message);
  }

  hideOverlay() {
    this.hideOverlay$.next(true);
  }
}
