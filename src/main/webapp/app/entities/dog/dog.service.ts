import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDog } from 'app/shared/model/dog.model';

type EntityResponseType = HttpResponse<IDog>;
type EntityArrayResponseType = HttpResponse<IDog[]>;

@Injectable({ providedIn: 'root' })
export class DogService {
    public resourceUrl = SERVER_API_URL + 'api/dogs';

    constructor(protected http: HttpClient) {}

    create(dog: IDog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dog);
        return this.http
            .post<IDog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(dog: IDog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dog);
        return this.http
            .put<IDog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDog[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(dog: IDog): IDog {
        const copy: IDog = Object.assign({}, dog, {
            birthday: dog.birthday != null && dog.birthday.isValid() ? dog.birthday.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.birthday = res.body.birthday != null ? moment(res.body.birthday) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((dog: IDog) => {
                dog.birthday = dog.birthday != null ? moment(dog.birthday) : null;
            });
        }
        return res;
    }
}
