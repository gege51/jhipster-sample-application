import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Familly } from 'app/shared/model/familly.model';
import { FamillyService } from './familly.service';
import { FamillyComponent } from './familly.component';
import { FamillyDetailComponent } from './familly-detail.component';
import { FamillyUpdateComponent } from './familly-update.component';
import { FamillyDeletePopupComponent } from './familly-delete-dialog.component';
import { IFamilly } from 'app/shared/model/familly.model';

@Injectable({ providedIn: 'root' })
export class FamillyResolve implements Resolve<IFamilly> {
    constructor(private service: FamillyService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFamilly> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Familly>) => response.ok),
                map((familly: HttpResponse<Familly>) => familly.body)
            );
        }
        return of(new Familly());
    }
}

export const famillyRoute: Routes = [
    {
        path: '',
        component: FamillyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Famillies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FamillyDetailComponent,
        resolve: {
            familly: FamillyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Famillies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FamillyUpdateComponent,
        resolve: {
            familly: FamillyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Famillies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FamillyUpdateComponent,
        resolve: {
            familly: FamillyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Famillies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const famillyPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FamillyDeletePopupComponent,
        resolve: {
            familly: FamillyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Famillies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
