import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Criteria } from 'app/shared/model/criteria.model';
import { CriteriaService } from './criteria.service';
import { CriteriaComponent } from './criteria.component';
import { CriteriaDetailComponent } from './criteria-detail.component';
import { CriteriaUpdateComponent } from './criteria-update.component';
import { CriteriaDeletePopupComponent } from './criteria-delete-dialog.component';
import { ICriteria } from 'app/shared/model/criteria.model';

@Injectable({ providedIn: 'root' })
export class CriteriaResolve implements Resolve<ICriteria> {
    constructor(private service: CriteriaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICriteria> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Criteria>) => response.ok),
                map((criteria: HttpResponse<Criteria>) => criteria.body)
            );
        }
        return of(new Criteria());
    }
}

export const criteriaRoute: Routes = [
    {
        path: '',
        component: CriteriaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Criteria'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CriteriaDetailComponent,
        resolve: {
            criteria: CriteriaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Criteria'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CriteriaUpdateComponent,
        resolve: {
            criteria: CriteriaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Criteria'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CriteriaUpdateComponent,
        resolve: {
            criteria: CriteriaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Criteria'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const criteriaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CriteriaDeletePopupComponent,
        resolve: {
            criteria: CriteriaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Criteria'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
