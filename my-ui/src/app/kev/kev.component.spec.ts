import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KevComponent } from './kev.component';

describe('KevComponent', () => {
  let component: KevComponent;
  let fixture: ComponentFixture<KevComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [KevComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(KevComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
