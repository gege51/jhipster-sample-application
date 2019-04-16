import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDog } from 'app/shared/model/dog.model';
import { DogService } from './dog.service';
import { IBilanCheck } from 'app/shared/model/bilan-check.model';
import { BilanCheckService } from 'app/entities/bilan-check';
import { IRace } from 'app/shared/model/race.model';
import { RaceService } from 'app/entities/race';
import { IColor } from 'app/shared/model/color.model';
import { ColorService } from 'app/entities/color';

@Component({
    selector: 'jhi-dog-update',
    templateUrl: './dog-update.component.html'
})
export class DogUpdateComponent implements OnInit {
    dog: IDog;
    isSaving: boolean;

    bilanchecks: IBilanCheck[];

    races: IRace[];

    colors: IColor[];
    birthday: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected dogService: DogService,
        protected bilanCheckService: BilanCheckService,
        protected raceService: RaceService,
        protected colorService: ColorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dog }) => {
            this.dog = dog;
            this.birthday = this.dog.birthday != null ? this.dog.birthday.format(DATE_TIME_FORMAT) : null;
        });
        this.bilanCheckService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBilanCheck[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBilanCheck[]>) => response.body)
            )
            .subscribe((res: IBilanCheck[]) => (this.bilanchecks = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.raceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRace[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRace[]>) => response.body)
            )
            .subscribe((res: IRace[]) => (this.races = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.colorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IColor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IColor[]>) => response.body)
            )
            .subscribe((res: IColor[]) => (this.colors = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.dog.birthday = this.birthday != null ? moment(this.birthday, DATE_TIME_FORMAT) : null;
        if (this.dog.id !== undefined) {
            this.subscribeToSaveResponse(this.dogService.update(this.dog));
        } else {
            this.subscribeToSaveResponse(this.dogService.create(this.dog));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDog>>) {
        result.subscribe((res: HttpResponse<IDog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBilanCheckById(index: number, item: IBilanCheck) {
        return item.id;
    }

    trackRaceById(index: number, item: IRace) {
        return item.id;
    }

    trackColorById(index: number, item: IColor) {
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
