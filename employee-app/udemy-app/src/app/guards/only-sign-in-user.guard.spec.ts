import { TestBed, async, inject } from '@angular/core/testing';

import { OnlySignInUserGuard } from './only-sign-in-user.guard';

describe('OnlySignInUserGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [OnlySignInUserGuard]
    });
  });

  it('should ...', inject([OnlySignInUserGuard], (guard: OnlySignInUserGuard) => {
    expect(guard).toBeTruthy();
  }));
});
