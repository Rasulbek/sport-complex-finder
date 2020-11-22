import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IImageStorage } from 'app/shared/model/image-storage.model';

type EntityResponseType = HttpResponse<IImageStorage>;
type EntityArrayResponseType = HttpResponse<IImageStorage[]>;

@Injectable({ providedIn: 'root' })
export class ImageStorageService {
  public resourceUrl = SERVER_API_URL + 'api/image-storages';

  constructor(protected http: HttpClient) {}

  create(imageStorage: IImageStorage): Observable<EntityResponseType> {
    return this.http.post<IImageStorage>(this.resourceUrl, imageStorage, { observe: 'response' });
  }

  update(imageStorage: IImageStorage): Observable<EntityResponseType> {
    return this.http.put<IImageStorage>(this.resourceUrl, imageStorage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IImageStorage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IImageStorage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
