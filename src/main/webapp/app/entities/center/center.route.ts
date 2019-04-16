import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Center } from 'app/shared/model/center.model';
import { CenterService } from './center.service';
import { CenterComponent } from './center.component';
import { CenterDetailComponent } from './center-detail.component';
import { CenterUpdateComponent } from './center-update.component';
import { CenterDeletePopupComponent } from './center-delete-dialog.component';
import { ICenter } from 'app/shared/model/center.model';

@Injectable({ providedIn: 'root' })
export class CenterResolve implements Resolve<ICenter> {
    constructor(private service: CenterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICenter> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Center>) => response.ok),
                map((center: HttpResponse<Center>) => center.body)
            );
        }
        return of(new Center());
    }
}

export const centerRoute: Routes = [
    {
        path: '',
        component: CenterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Centers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CenterDetailComponent,
        resolve: {
            center: CenterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Centers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CenterUpdateComponent,
        resolve: {
            center: CenterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Centers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CenterUpdateComponent,
        resolve: {
            center: CenterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Centers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const centerPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CenterDeletePopupComponent,
        resolve: {
            center: CenterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Centers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
