import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetFicheroComponent } from './get-fichero.component';

describe('GetFicheroComponent', () => {
  let component: GetFicheroComponent;
  let fixture: ComponentFixture<GetFicheroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GetFicheroComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GetFicheroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
