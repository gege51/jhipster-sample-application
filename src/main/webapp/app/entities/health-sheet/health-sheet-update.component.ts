import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IHealthSheet } from 'app/shared/model/health-sheet.model';
import { HealthSheetService } from './health-sheet.service';
import { IDog } from 'app/shared/model/dog.model';
import { DogService } from 'app/entities/dog';

@Component({
    selector: 'jhi-health-sheet-update',
    templateUrl: './health-sheet-update.component.html'
})
export class HealthSheetUpdateComponent implements OnInit {
    healthSheet: IHealthSheet;
    isSaving: boolean;

    dogs: IDog[];
    date: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected healthSheetService: HealthSheetService,
        protected dogService: DogService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ healthSheet }) => {
            this.healthSheet = healthSheet;
            this.date = this.healthSheet.date != null ? this.healthSheet.date.format(DATE_TIME_FORMAT) : null;
        });
        this.dogService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDog[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDog[]>) => response.body)
            )
            .subscribe((res: IDog[]) => (this.dogs = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.healthSheet.date = this.date != null ? moment(this.date, DATE_TIME_FORMAT) : null;
        if (this.healthSheet.id !== undefined) {
            this.subscribeToSaveResponse(this.healthSheetService.update(this.healthSheet));
        } else {
            this.subscribeToSaveResponse(this.healthSheetService.create(this.healthSheet));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHealthSheet>>) {
        result.subscribe((res: HttpResponse<IHealthSheet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
