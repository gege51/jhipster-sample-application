import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICenter } from 'app/shared/model/center.model';

type EntityResponseType = HttpResponse<ICenter>;
type EntityArrayResponseType = HttpResponse<ICenter[]>;

@Injectable({ providedIn: 'root' })
export class CenterService {
    public resourceUrl = SERVER_API_URL + 'api/centers';

    constructor(protected http: HttpClient) {}

    create(center: ICenter): Observable<EntityResponseType> {
        return this.http.post<ICenter>(this.resourceUrl, center, { observe: 'response' });
    }

    update(center: ICenter): Observable<EntityResponseType> {
        return this.http.put<ICenter>(this.resourceUrl, center, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICenter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICenter[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
