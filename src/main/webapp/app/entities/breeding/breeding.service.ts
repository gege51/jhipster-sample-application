import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBreeding } from 'app/shared/model/breeding.model';

type EntityResponseType = HttpResponse<IBreeding>;
type EntityArrayResponseType = HttpResponse<IBreeding[]>;

@Injectable({ providedIn: 'root' })
export class BreedingService {
    public resourceUrl = SERVER_API_URL + 'api/breedings';

    constructor(protected http: HttpClient) {}

    create(breeding: IBreeding): Observable<EntityResponseType> {
        return this.http.post<IBreeding>(this.resourceUrl, breeding, { observe: 'response' });
    }

    update(breeding: IBreeding): Observable<EntityResponseType> {
        return this.http.put<IBreeding>(this.resourceUrl, breeding, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBreeding>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBreeding[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
