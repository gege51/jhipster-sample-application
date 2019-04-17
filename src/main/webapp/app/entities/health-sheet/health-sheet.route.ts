import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HealthSheet } from 'app/shared/model/health-sheet.model';
import { HealthSheetService } from './health-sheet.service';
import { HealthSheetComponent } from './health-sheet.component';
import { HealthSheetDetailComponent } from './health-sheet-detail.component';
import { HealthSheetUpdateComponent } from './health-sheet-update.component';
import { HealthSheetDeletePopupComponent } from './health-sheet-delete-dialog.component';
import { IHealthSheet } from 'app/shared/model/health-sheet.model';

@Injectable({ providedIn: 'root' })
export class HealthSheetResolve implements Resolve<IHealthSheet> {
    constructor(private service: HealthSheetService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHealthSheet> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<HealthSheet>) => response.ok),
                map((healthSheet: HttpResponse<HealthSheet>) => healthSheet.body)
            );
        }
        return of(new HealthSheet());
    }
}

export const healthSheetRoute: Routes = [
    {
        path: '',
        component: HealthSheetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HealthSheets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HealthSheetDetailComponent,
        resolve: {
            healthSheet: HealthSheetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HealthSheets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HealthSheetUpdateComponent,
        resolve: {
            healthSheet: HealthSheetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HealthSheets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HealthSheetUpdateComponent,
        resolve: {
            healthSheet: HealthSheetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HealthSheets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const healthSheetPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HealthSheetDeletePopupComponent,
        resolve: {
            healthSheet: HealthSheetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HealthSheets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
