import { TestBed } from '@angular/core/testing';

import { FicheroService } from './fichero.service';

describe('FicheroService', () => {
  let service: FicheroService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FicheroService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
