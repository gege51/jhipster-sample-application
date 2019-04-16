import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BilanCheck } from 'app/shared/model/bilan-check.model';
import { BilanCheckService } from './bilan-check.service';
import { BilanCheckComponent } from './bilan-check.component';
import { BilanCheckDetailComponent } from './bilan-check-detail.component';
import { BilanCheckUpdateComponent } from './bilan-check-update.component';
import { BilanCheckDeletePopupComponent } from './bilan-check-delete-dialog.component';
import { IBilanCheck } from 'app/shared/model/bilan-check.model';

@Injectable({ providedIn: 'root' })
export class BilanCheckResolve implements Resolve<IBilanCheck> {
    constructor(private service: BilanCheckService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBilanCheck> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BilanCheck>) => response.ok),
                map((bilanCheck: HttpResponse<BilanCheck>) => bilanCheck.body)
            );
        }
        return of(new BilanCheck());
    }
}

export const bilanCheckRoute: Routes = [
    {
        path: '',
        component: BilanCheckComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BilanChecks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BilanCheckDetailComponent,
        resolve: {
            bilanCheck: BilanCheckResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BilanChecks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BilanCheckUpdateComponent,
        resolve: {
            bilanCheck: BilanCheckResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BilanChecks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BilanCheckUpdateComponent,
        resolve: {
            bilanCheck: BilanCheckResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BilanChecks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bilanCheckPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BilanCheckDeletePopupComponent,
        resolve: {
            bilanCheck: BilanCheckResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BilanChecks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
