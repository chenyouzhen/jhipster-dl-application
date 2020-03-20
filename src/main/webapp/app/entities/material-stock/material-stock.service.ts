import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMaterialStock } from 'app/shared/model/material-stock.model';

type EntityResponseType = HttpResponse<IMaterialStock>;
type EntityArrayResponseType = HttpResponse<IMaterialStock[]>;

@Injectable({ providedIn: 'root' })
export class MaterialStockService {
  public resourceUrl = SERVER_API_URL + 'api/material-stocks';

  constructor(protected http: HttpClient) {}

  create(materialStock: IMaterialStock): Observable<EntityResponseType> {
    return this.http.post<IMaterialStock>(this.resourceUrl, materialStock, { observe: 'response' });
  }

  update(materialStock: IMaterialStock): Observable<EntityResponseType> {
    return this.http.put<IMaterialStock>(this.resourceUrl, materialStock, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMaterialStock>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMaterialStock[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
