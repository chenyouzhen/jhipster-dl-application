import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AlarmService } from 'app/entities/alarm/alarm.service';
import { IAlarm, Alarm } from 'app/shared/model/alarm.model';

describe('Service Tests', () => {
  describe('Alarm Service', () => {
    let injector: TestBed;
    let service: AlarmService;
    let httpMock: HttpTestingController;
    let elemDefault: IAlarm;
    let expectedResult: IAlarm | IAlarm[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AlarmService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Alarm(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            resultTime: currentDate.format(DATE_TIME_FORMAT),
            resolveTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Alarm', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            resultTime: currentDate.format(DATE_TIME_FORMAT),
            resolveTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            resultTime: currentDate,
            resolveTime: currentDate
          },
          returnedFromService
        );

        service.create(new Alarm()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Alarm', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            result: 'BBBBBB',
            cause: 'BBBBBB',
            resultTime: currentDate.format(DATE_TIME_FORMAT),
            level: 'BBBBBB',
            status: 'BBBBBB',
            resolveTime: currentDate.format(DATE_TIME_FORMAT),
            sourceType: 'BBBBBB',
            sourceId: 'BBBBBB',
            details: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            resultTime: currentDate,
            resolveTime: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Alarm', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            result: 'BBBBBB',
            cause: 'BBBBBB',
            resultTime: currentDate.format(DATE_TIME_FORMAT),
            level: 'BBBBBB',
            status: 'BBBBBB',
            resolveTime: currentDate.format(DATE_TIME_FORMAT),
            sourceType: 'BBBBBB',
            sourceId: 'BBBBBB',
            details: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            resultTime: currentDate,
            resolveTime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Alarm', () => {
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
