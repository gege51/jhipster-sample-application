import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRace } from 'app/shared/model/race.model';

type EntityResponseType = HttpResponse<IRace>;
type EntityArrayResponseType = HttpResponse<IRace[]>;

@Injectable({ providedIn: 'root' })
export class RaceService {
    public resourceUrl = SERVER_API_URL + 'api/races';

    constructor(protected http: HttpClient) {}

    create(race: IRace): Observable<EntityResponseType> {
        return this.http.post<IRace>(this.resourceUrl, race, { observe: 'response' });
    }

    update(race: IRace): Observable<EntityResponseType> {
        return this.http.put<IRace>(this.resourceUrl, race, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRace>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRace[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
