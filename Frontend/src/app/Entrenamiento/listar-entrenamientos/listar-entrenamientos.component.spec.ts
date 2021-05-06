import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarEntrenamientosComponent } from './listar-entrenamientos.component';

describe('ListarEntrenamientosComponent', () => {
  let component: ListarEntrenamientosComponent;
  let fixture: ComponentFixture<ListarEntrenamientosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListarEntrenamientosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarEntrenamientosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
