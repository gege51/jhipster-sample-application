import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBilanCheck } from 'app/shared/model/bilan-check.model';

type EntityResponseType = HttpResponse<IBilanCheck>;
type EntityArrayResponseType = HttpResponse<IBilanCheck[]>;

@Injectable({ providedIn: 'root' })
export class BilanCheckService {
    public resourceUrl = SERVER_API_URL + 'api/bilan-checks';

    constructor(protected http: HttpClient) {}

    create(bilanCheck: IBilanCheck): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bilanCheck);
        return this.http
            .post<IBilanCheck>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(bilanCheck: IBilanCheck): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bilanCheck);
        return this.http
            .put<IBilanCheck>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBilanCheck>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBilanCheck[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(bilanCheck: IBilanCheck): IBilanCheck {
        const copy: IBilanCheck = Object.assign({}, bilanCheck, {
            date: bilanCheck.date != null && bilanCheck.date.isValid() ? bilanCheck.date.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((bilanCheck: IBilanCheck) => {
                bilanCheck.date = bilanCheck.date != null ? moment(bilanCheck.date) : null;
            });
        }
        return res;
    }
}
