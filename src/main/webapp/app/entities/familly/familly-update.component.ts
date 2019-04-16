import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFamilly } from 'app/shared/model/familly.model';
import { FamillyService } from './familly.service';
import { IDog } from 'app/shared/model/dog.model';
import { DogService } from 'app/entities/dog';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location';

@Component({
    selector: 'jhi-familly-update',
    templateUrl: './familly-update.component.html'
})
export class FamillyUpdateComponent implements OnInit {
    familly: IFamilly;
    isSaving: boolean;

    dogs: IDog[];

    locations: ILocation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected famillyService: FamillyService,
        protected dogService: DogService,
        protected locationService: LocationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ familly }) => {
            this.familly = familly;
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
        if (this.familly.id !== undefined) {
            this.subscribeToSaveResponse(this.famillyService.update(this.familly));
        } else {
            this.subscribeToSaveResponse(this.famillyService.create(this.familly));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamilly>>) {
        result.subscribe((res: HttpResponse<IFamilly>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
