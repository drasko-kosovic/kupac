import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ViewkorpaService } from 'app/entities/viewkorpa/viewkorpa.service';
import { IViewkorpa, Viewkorpa } from 'app/shared/model/viewkorpa.model';

describe('Service Tests', () => {
  describe('Viewkorpa Service', () => {
    let injector: TestBed;
    let service: ViewkorpaService;
    let httpMock: HttpTestingController;
    let elemDefault: IViewkorpa;
    let expectedResult: IViewkorpa | IViewkorpa[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ViewkorpaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Viewkorpa(0, 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should return a list of Viewkorpa', () => {
        const returnedFromService = Object.assign(
          {
            artikal: 'BBBBBB',
            cijena: 1,
            ukupno: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
