import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KupacTestModule } from '../../../test.module';
import { KorpaDetailComponent } from 'app/entities/korpa/korpa-detail.component';
import { Korpa } from 'app/shared/model/korpa.model';

describe('Component Tests', () => {
  describe('Korpa Management Detail Component', () => {
    let comp: KorpaDetailComponent;
    let fixture: ComponentFixture<KorpaDetailComponent>;
    const route = ({ data: of({ korpa: new Korpa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KupacTestModule],
        declarations: [KorpaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(KorpaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KorpaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load korpa on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.korpa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
