import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KupacTestModule } from '../../../test.module';
import { AngularComponent } from 'app/entities/angular/angular.component';
import { AngularService } from 'app/entities/angular/angular.service';
import { Angular } from 'app/shared/model/angular.model';

describe('Component Tests', () => {
  describe('Angular Management Component', () => {
    let comp: AngularComponent;
    let fixture: ComponentFixture<AngularComponent>;
    let service: AngularService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KupacTestModule],
        declarations: [AngularComponent],
      })
        .overrideTemplate(AngularComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AngularComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AngularService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Angular(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.angulars && comp.angulars[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
