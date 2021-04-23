import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarFicherosComponent } from './listar-ficheros.component';

describe('ListarFicherosComponent', () => {
  let component: ListarFicherosComponent;
  let fixture: ComponentFixture<ListarFicherosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListarFicherosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarFicherosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
