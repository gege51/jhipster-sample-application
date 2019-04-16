import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFamilly } from 'app/shared/model/familly.model';

type EntityResponseType = HttpResponse<IFamilly>;
type EntityArrayResponseType = HttpResponse<IFamilly[]>;

@Injectable({ providedIn: 'root' })
export class FamillyService {
    public resourceUrl = SERVER_API_URL + 'api/famillies';

    constructor(protected http: HttpClient) {}

    create(familly: IFamilly): Observable<EntityResponseType> {
        return this.http.post<IFamilly>(this.resourceUrl, familly, { observe: 'response' });
    }

    update(familly: IFamilly): Observable<EntityResponseType> {
        return this.http.put<IFamilly>(this.resourceUrl, familly, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFamilly>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFamilly[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
