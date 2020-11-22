import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISportsHall } from 'app/shared/model/sports-hall.model';

type EntityResponseType = HttpResponse<ISportsHall>;
type EntityArrayResponseType = HttpResponse<ISportsHall[]>;

@Injectable({ providedIn: 'root' })
export class SportsHallService {
  public resourceUrl = SERVER_API_URL + 'api/sports-halls';

  constructor(protected http: HttpClient) {}

  create(sportsHall: ISportsHall): Observable<EntityResponseType> {
    return this.http.post<ISportsHall>(this.resourceUrl, sportsHall, { observe: 'response' });
  }

  update(sportsHall: ISportsHall): Observable<EntityResponseType> {
    return this.http.put<ISportsHall>(this.resourceUrl, sportsHall, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISportsHall>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISportsHall[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
