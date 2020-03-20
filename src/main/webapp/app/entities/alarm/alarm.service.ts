import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlarm } from 'app/shared/model/alarm.model';

type EntityResponseType = HttpResponse<IAlarm>;
type EntityArrayResponseType = HttpResponse<IAlarm[]>;

@Injectable({ providedIn: 'root' })
export class AlarmService {
  public resourceUrl = SERVER_API_URL + 'api/alarms';

  constructor(protected http: HttpClient) {}

  create(alarm: IAlarm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alarm);
    return this.http
      .post<IAlarm>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(alarm: IAlarm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alarm);
    return this.http
      .put<IAlarm>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAlarm>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAlarm[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(alarm: IAlarm): IAlarm {
    const copy: IAlarm = Object.assign({}, alarm, {
      resultTime: alarm.resultTime && alarm.resultTime.isValid() ? alarm.resultTime.toJSON() : undefined,
      resolveTime: alarm.resolveTime && alarm.resolveTime.isValid() ? alarm.resolveTime.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.resultTime = res.body.resultTime ? moment(res.body.resultTime) : undefined;
      res.body.resolveTime = res.body.resolveTime ? moment(res.body.resolveTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((alarm: IAlarm) => {
        alarm.resultTime = alarm.resultTime ? moment(alarm.resultTime) : undefined;
        alarm.resolveTime = alarm.resolveTime ? moment(alarm.resolveTime) : undefined;
      });
    }
    return res;
  }
}
