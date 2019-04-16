import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Race } from 'app/shared/model/race.model';
import { RaceService } from './race.service';
import { RaceComponent } from './race.component';
import { RaceDetailComponent } from './race-detail.component';
import { RaceUpdateComponent } from './race-update.component';
import { RaceDeletePopupComponent } from './race-delete-dialog.component';
import { IRace } from 'app/shared/model/race.model';

@Injectable({ providedIn: 'root' })
export class RaceResolve implements Resolve<IRace> {
    constructor(private service: RaceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRace> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Race>) => response.ok),
                map((race: HttpResponse<Race>) => race.body)
            );
        }
        return of(new Race());
    }
}

export const raceRoute: Routes = [
    {
        path: '',
        component: RaceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Races'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RaceDetailComponent,
        resolve: {
            race: RaceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Races'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RaceUpdateComponent,
        resolve: {
            race: RaceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Races'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RaceUpdateComponent,
        resolve: {
            race: RaceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Races'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const racePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RaceDeletePopupComponent,
        resolve: {
            race: RaceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Races'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
