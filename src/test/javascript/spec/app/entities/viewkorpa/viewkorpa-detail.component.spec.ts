import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KupacTestModule } from '../../../test.module';
import { ViewkorpaDetailComponent } from 'app/entities/viewkorpa/viewkorpa-detail.component';
import { Viewkorpa } from 'app/shared/model/viewkorpa.model';

describe('Component Tests', () => {
  describe('Viewkorpa Management Detail Component', () => {
    let comp: ViewkorpaDetailComponent;
    let fixture: ComponentFixture<ViewkorpaDetailComponent>;
    const route = ({ data: of({ viewkorpa: new Viewkorpa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KupacTestModule],
        declarations: [ViewkorpaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ViewkorpaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ViewkorpaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load viewkorpa on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.viewkorpa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
