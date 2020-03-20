import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFactory } from 'app/shared/model/factory.model';

type EntityResponseType = HttpResponse<IFactory>;
type EntityArrayResponseType = HttpResponse<IFactory[]>;

@Injectable({ providedIn: 'root' })
export class FactoryService {
  public resourceUrl = SERVER_API_URL + 'api/factories';

  constructor(protected http: HttpClient) {}

  create(factory: IFactory): Observable<EntityResponseType> {
    return this.http.post<IFactory>(this.resourceUrl, factory, { observe: 'response' });
  }

  update(factory: IFactory): Observable<EntityResponseType> {
    return this.http.put<IFactory>(this.resourceUrl, factory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFactory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFactory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
