import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LogisticsService } from 'app/entities/logistics/logistics.service';
import { ILogistics, Logistics } from 'app/shared/model/logistics.model';

describe('Service Tests', () => {
  describe('Logistics Service', () => {
    let injector: TestBed;
    let service: LogisticsService;
    let httpMock: HttpTestingController;
    let elemDefault: ILogistics;
    let expectedResult: ILogistics | ILogistics[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LogisticsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Logistics(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Logistics', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Logistics()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Logistics', () => {
        const returnedFromService = Object.assign(
          {
            logisticsId: 'BBBBBB',
            expressCompany: 'BBBBBB',
            expressNumber: 'BBBBBB',
            status: 'BBBBBB',
            startPosition: 'BBBBBB',
            endPosition: 'BBBBBB',
            currentPosition: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Logistics', () => {
        const returnedFromService = Object.assign(
          {
            logisticsId: 'BBBBBB',
            expressCompany: 'BBBBBB',
            expressNumber: 'BBBBBB',
            status: 'BBBBBB',
            startPosition: 'BBBBBB',
            endPosition: 'BBBBBB',
            currentPosition: 'BBBBBB'
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

      it('should delete a Logistics', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
