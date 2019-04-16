import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBilanType } from 'app/shared/model/bilan-type.model';

type EntityResponseType = HttpResponse<IBilanType>;
type EntityArrayResponseType = HttpResponse<IBilanType[]>;

@Injectable({ providedIn: 'root' })
export class BilanTypeService {
    public resourceUrl = SERVER_API_URL + 'api/bilan-types';

    constructor(protected http: HttpClient) {}

    create(bilanType: IBilanType): Observable<EntityResponseType> {
        return this.http.post<IBilanType>(this.resourceUrl, bilanType, { observe: 'response' });
    }

    update(bilanType: IBilanType): Observable<EntityResponseType> {
        return this.http.put<IBilanType>(this.resourceUrl, bilanType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBilanType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBilanType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
