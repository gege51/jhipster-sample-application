import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BilanType } from 'app/shared/model/bilan-type.model';
import { BilanTypeService } from './bilan-type.service';
import { BilanTypeComponent } from './bilan-type.component';
import { BilanTypeDetailComponent } from './bilan-type-detail.component';
import { BilanTypeUpdateComponent } from './bilan-type-update.component';
import { BilanTypeDeletePopupComponent } from './bilan-type-delete-dialog.component';
import { IBilanType } from 'app/shared/model/bilan-type.model';

@Injectable({ providedIn: 'root' })
export class BilanTypeResolve implements Resolve<IBilanType> {
    constructor(private service: BilanTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBilanType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BilanType>) => response.ok),
                map((bilanType: HttpResponse<BilanType>) => bilanType.body)
            );
        }
        return of(new BilanType());
    }
}

export const bilanTypeRoute: Routes = [
    {
        path: '',
        component: BilanTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BilanTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BilanTypeDetailComponent,
        resolve: {
            bilanType: BilanTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BilanTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BilanTypeUpdateComponent,
        resolve: {
            bilanType: BilanTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BilanTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BilanTypeUpdateComponent,
        resolve: {
            bilanType: BilanTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BilanTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bilanTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BilanTypeDeletePopupComponent,
        resolve: {
            bilanType: BilanTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BilanTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
