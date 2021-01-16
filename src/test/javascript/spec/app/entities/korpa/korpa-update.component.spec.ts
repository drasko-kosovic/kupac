import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KupacTestModule } from '../../../test.module';
import { KorpaUpdateComponent } from 'app/entities/korpa/korpa-update.component';
import { KorpaService } from 'app/entities/korpa/korpa.service';
import { Korpa } from 'app/shared/model/korpa.model';

describe('Component Tests', () => {
  describe('Korpa Management Update Component', () => {
    let comp: KorpaUpdateComponent;
    let fixture: ComponentFixture<KorpaUpdateComponent>;
    let service: KorpaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KupacTestModule],
        declarations: [KorpaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(KorpaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KorpaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KorpaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Korpa(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Korpa();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
