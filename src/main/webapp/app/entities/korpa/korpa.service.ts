import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IKorpa } from 'app/shared/model/korpa.model';

type EntityResponseType = HttpResponse<IKorpa>;
type EntityArrayResponseType = HttpResponse<IKorpa[]>;

@Injectable({ providedIn: 'root' })
export class KorpaService {
  public resourceUrl = SERVER_API_URL + 'api/korpas';

  constructor(protected http: HttpClient) {}

  create(korpa: IKorpa): Observable<EntityResponseType> {
    return this.http.post<IKorpa>(this.resourceUrl, korpa, { observe: 'response' });
  }

  update(korpa: IKorpa): Observable<EntityResponseType> {
    return this.http.put<IKorpa>(this.resourceUrl, korpa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKorpa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKorpa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  reportServiceArtikal(artikal: String | undefined): any {
    const httpOptions = {
      responseType: 'arraybuffer' as 'json',
      // 'responseType'  : 'blob' as 'json'        //This also worked
    };
    return this.http.get<any>('http://localhost:8080/report/korpa/' + artikal, httpOptions);
  }

  reportServiceArtikalAll(): Observable<any> {
    return this.http.get('http://localhost:8080/report/korpa', {
      observe: 'response',
      responseType: 'blob',
    });
  }

  exelServiceArtikal(artikal: String | undefined): any {
    return this.http.get<any>('http://localhost:8080/api/exel/download' + artikal);
  }
}
