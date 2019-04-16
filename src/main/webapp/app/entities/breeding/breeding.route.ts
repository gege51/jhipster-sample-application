import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Breeding } from 'app/shared/model/breeding.model';
import { BreedingService } from './breeding.service';
import { BreedingComponent } from './breeding.component';
import { BreedingDetailComponent } from './breeding-detail.component';
import { BreedingUpdateComponent } from './breeding-update.component';
import { BreedingDeletePopupComponent } from './breeding-delete-dialog.component';
import { IBreeding } from 'app/shared/model/breeding.model';

@Injectable({ providedIn: 'root' })
export class BreedingResolve implements Resolve<IBreeding> {
    constructor(private service: BreedingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBreeding> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Breeding>) => response.ok),
                map((breeding: HttpResponse<Breeding>) => breeding.body)
            );
        }
        return of(new Breeding());
    }
}

export const breedingRoute: Routes = [
    {
        path: '',
        component: BreedingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Breedings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BreedingDetailComponent,
        resolve: {
            breeding: BreedingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Breedings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BreedingUpdateComponent,
        resolve: {
            breeding: BreedingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Breedings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BreedingUpdateComponent,
        resolve: {
            breeding: BreedingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Breedings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const breedingPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BreedingDeletePopupComponent,
        resolve: {
            breeding: BreedingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Breedings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
