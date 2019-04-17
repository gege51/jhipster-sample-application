import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBilanType } from 'app/shared/model/bilan-type.model';
import { BilanTypeService } from './bilan-type.service';
import { IBilanCheck } from 'app/shared/model/bilan-check.model';
import { BilanCheckService } from 'app/entities/bilan-check';

@Component({
    selector: 'jhi-bilan-type-update',
    templateUrl: './bilan-type-update.component.html'
})
export class BilanTypeUpdateComponent implements OnInit {
    bilanType: IBilanType;
    isSaving: boolean;

    bilanchecks: IBilanCheck[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected bilanTypeService: BilanTypeService,
        protected bilanCheckService: BilanCheckService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bilanType }) => {
            this.bilanType = bilanType;
        });
        this.bilanCheckService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBilanCheck[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBilanCheck[]>) => response.body)
            )
            .subscribe((res: IBilanCheck[]) => (this.bilanchecks = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bilanType.id !== undefined) {
            this.subscribeToSaveResponse(this.bilanTypeService.update(this.bilanType));
        } else {
            this.subscribeToSaveResponse(this.bilanTypeService.create(this.bilanType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBilanType>>) {
        result.subscribe((res: HttpResponse<IBilanType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
