import { TestBed } from '@angular/core/testing';

import { EntrenamientoService } from './entrenamiento.service';

describe('EntrenamientoService', () => {
  let service: EntrenamientoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EntrenamientoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
