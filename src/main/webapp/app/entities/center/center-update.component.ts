import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICenter } from 'app/shared/model/center.model';
import { CenterService } from './center.service';
import { IDog } from 'app/shared/model/dog.model';
import { DogService } from 'app/entities/dog';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location';

@Component({
    selector: 'jhi-center-update',
    templateUrl: './center-update.component.html'
})
export class CenterUpdateComponent implements OnInit {
    center: ICenter;
    isSaving: boolean;

    dogs: IDog[];

    locations: ILocation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected centerService: CenterService,
        protected dogService: DogService,
        protected locationService: LocationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ center }) => {
            this.center = center;
        });
        this.dogService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDog[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDog[]>) => response.body)
            )
            .subscribe((res: IDog[]) => (this.dogs = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.locationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ILocation[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILocation[]>) => response.body)
            )
            .subscribe((res: ILocation[]) => (this.locations = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.center.id !== undefined) {
            this.subscribeToSaveResponse(this.centerService.update(this.center));
        } else {
            this.subscribeToSaveResponse(this.centerService.create(this.center));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICenter>>) {
        result.subscribe((res: HttpResponse<ICenter>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDogById(index: number, item: IDog) {
        return item.id;
    }

    trackLocationById(index: number, item: ILocation) {
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
