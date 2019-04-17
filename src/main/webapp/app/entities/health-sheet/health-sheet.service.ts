import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHealthSheet } from 'app/shared/model/health-sheet.model';

type EntityResponseType = HttpResponse<IHealthSheet>;
type EntityArrayResponseType = HttpResponse<IHealthSheet[]>;

@Injectable({ providedIn: 'root' })
export class HealthSheetService {
    public resourceUrl = SERVER_API_URL + 'api/health-sheets';

    constructor(protected http: HttpClient) {}

    create(healthSheet: IHealthSheet): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(healthSheet);
        return this.http
            .post<IHealthSheet>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(healthSheet: IHealthSheet): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(healthSheet);
        return this.http
            .put<IHealthSheet>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHealthSheet>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHealthSheet[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(healthSheet: IHealthSheet): IHealthSheet {
        const copy: IHealthSheet = Object.assign({}, healthSheet, {
            date: healthSheet.date != null && healthSheet.date.isValid() ? healthSheet.date.toJSON() : null
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
            res.body.forEach((healthSheet: IHealthSheet) => {
                healthSheet.date = healthSheet.date != null ? moment(healthSheet.date) : null;
            });
        }
        return res;
    }
}
