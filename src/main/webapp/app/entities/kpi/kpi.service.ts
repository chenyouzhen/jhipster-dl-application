import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IKpi } from 'app/shared/model/kpi.model';

type EntityResponseType = HttpResponse<IKpi>;
type EntityArrayResponseType = HttpResponse<IKpi[]>;

@Injectable({ providedIn: 'root' })
export class KpiService {
  public resourceUrl = SERVER_API_URL + 'api/kpis';

  constructor(protected http: HttpClient) {}

  create(kpi: IKpi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kpi);
    return this.http
      .post<IKpi>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(kpi: IKpi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kpi);
    return this.http
      .put<IKpi>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IKpi>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IKpi[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(kpi: IKpi): IKpi {
    const copy: IKpi = Object.assign({}, kpi, {
      resultTime: kpi.resultTime && kpi.resultTime.isValid() ? kpi.resultTime.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.resultTime = res.body.resultTime ? moment(res.body.resultTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((kpi: IKpi) => {
        kpi.resultTime = kpi.resultTime ? moment(kpi.resultTime) : undefined;
      });
    }
    return res;
  }
}
