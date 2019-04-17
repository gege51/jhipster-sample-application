import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from './location.service';
import { ICenter } from 'app/shared/model/center.model';
import { CenterService } from 'app/entities/center';
import { IBreeding } from 'app/shared/model/breeding.model';
import { BreedingService } from 'app/entities/breeding';
import { IFamilly } from 'app/shared/model/familly.model';
import { FamillyService } from 'app/entities/familly';

@Component({
    selector: 'jhi-location-update',
    templateUrl: './location-update.component.html'
})
export class LocationUpdateComponent implements OnInit {
    location: ILocation;
    isSaving: boolean;

    centers: ICenter[];

    breedings: IBreeding[];

    famillies: IFamilly[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected locationService: LocationService,
        protected centerService: CenterService,
        protected breedingService: BreedingService,
        protected famillyService: FamillyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ location }) => {
            this.location = location;
        });
        this.centerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICenter[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICenter[]>) => response.body)
            )
            .subscribe((res: ICenter[]) => (this.centers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.breedingService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBreeding[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBreeding[]>) => response.body)
            )
            .subscribe((res: IBreeding[]) => (this.breedings = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.famillyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFamilly[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFamilly[]>) => response.body)
            )
            .subscribe((res: IFamilly[]) => (this.famillies = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.location.id !== undefined) {
            this.subscribeToSaveResponse(this.locationService.update(this.location));
        } else {
            this.subscribeToSaveResponse(this.locationService.create(this.location));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocation>>) {
        result.subscribe((res: HttpResponse<ILocation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCenterById(index: number, item: ICenter) {
        return item.id;
    }

    trackBreedingById(index: number, item: IBreeding) {
        return item.id;
    }

    trackFamillyById(index: number, item: IFamilly) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
