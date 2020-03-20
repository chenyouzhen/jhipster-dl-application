import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductStock } from 'app/shared/model/product-stock.model';

type EntityResponseType = HttpResponse<IProductStock>;
type EntityArrayResponseType = HttpResponse<IProductStock[]>;

@Injectable({ providedIn: 'root' })
export class ProductStockService {
  public resourceUrl = SERVER_API_URL + 'api/product-stocks';

  constructor(protected http: HttpClient) {}

  create(productStock: IProductStock): Observable<EntityResponseType> {
    return this.http.post<IProductStock>(this.resourceUrl, productStock, { observe: 'response' });
  }

  update(productStock: IProductStock): Observable<EntityResponseType> {
    return this.http.put<IProductStock>(this.resourceUrl, productStock, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductStock>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductStock[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
