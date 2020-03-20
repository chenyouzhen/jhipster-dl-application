import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAssemblyLine } from 'app/shared/model/assembly-line.model';

type EntityResponseType = HttpResponse<IAssemblyLine>;
type EntityArrayResponseType = HttpResponse<IAssemblyLine[]>;

@Injectable({ providedIn: 'root' })
export class AssemblyLineService {
  public resourceUrl = SERVER_API_URL + 'api/assembly-lines';

  constructor(protected http: HttpClient) {}

  create(assemblyLine: IAssemblyLine): Observable<EntityResponseType> {
    return this.http.post<IAssemblyLine>(this.resourceUrl, assemblyLine, { observe: 'response' });
  }

  update(assemblyLine: IAssemblyLine): Observable<EntityResponseType> {
    return this.http.put<IAssemblyLine>(this.resourceUrl, assemblyLine, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAssemblyLine>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAssemblyLine[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
