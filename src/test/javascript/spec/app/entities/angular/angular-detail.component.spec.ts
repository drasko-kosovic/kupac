import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KupacTestModule } from '../../../test.module';
import { AngularDetailComponent } from 'app/entities/angular/angular-detail.component';
import { Angular } from 'app/shared/model/angular.model';

describe('Component Tests', () => {
  describe('Angular Management Detail Component', () => {
    let comp: AngularDetailComponent;
    let fixture: ComponentFixture<AngularDetailComponent>;
    const route = ({ data: of({ angular: new Angular(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KupacTestModule],
        declarations: [AngularDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AngularDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AngularDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load angular on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.angular).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
