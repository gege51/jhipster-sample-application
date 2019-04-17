import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IBilanCheck } from 'app/shared/model/bilan-check.model';
import { BilanCheckService } from './bilan-check.service';
import { IBilanType } from 'app/shared/model/bilan-type.model';
import { BilanTypeService } from 'app/entities/bilan-type';
import { ICriteria } from 'app/shared/model/criteria.model';
import { CriteriaService } from 'app/entities/criteria';

@Component({
    selector: 'jhi-bilan-check-update',
    templateUrl: './bilan-check-update.component.html'
})
export class BilanCheckUpdateComponent implements OnInit {
    bilanCheck: IBilanCheck;
    isSaving: boolean;

    bilantypes: IBilanType[];

    criteria: ICriteria[];
    date: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected bilanCheckService: BilanCheckService,
        protected bilanTypeService: BilanTypeService,
        protected criteriaService: CriteriaService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bilanCheck }) => {
            this.bilanCheck = bilanCheck;
            this.date = this.bilanCheck.date != null ? this.bilanCheck.date.format(DATE_TIME_FORMAT) : null;
        });
        this.bilanTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBilanType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBilanType[]>) => response.body)
            )
            .subscribe((res: IBilanType[]) => (this.bilantypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.criteriaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICriteria[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICriteria[]>) => response.body)
            )
            .subscribe((res: ICriteria[]) => (this.criteria = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.bilanCheck.date = this.date != null ? moment(this.date, DATE_TIME_FORMAT) : null;
        if (this.bilanCheck.id !== undefined) {
            this.subscribeToSaveResponse(this.bilanCheckService.update(this.bilanCheck));
        } else {
            this.subscribeToSaveResponse(this.bilanCheckService.create(this.bilanCheck));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBilanCheck>>) {
        result.subscribe((res: HttpResponse<IBilanCheck>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBilanTypeById(index: number, item: IBilanType) {
        return item.id;
    }

    trackCriteriaById(index: number, item: ICriteria) {
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
