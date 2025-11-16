import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Repartidores } from './repartidores';

describe('Repartidores', () => {
  let component: Repartidores;
  let fixture: ComponentFixture<Repartidores>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Repartidores]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Repartidores);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
