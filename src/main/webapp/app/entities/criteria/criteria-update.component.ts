import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICriteria } from 'app/shared/model/criteria.model';
import { CriteriaService } from './criteria.service';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from 'app/entities/categorie';
import { IBilanCheck } from 'app/shared/model/bilan-check.model';
import { BilanCheckService } from 'app/entities/bilan-check';

@Component({
    selector: 'jhi-criteria-update',
    templateUrl: './criteria-update.component.html'
})
export class CriteriaUpdateComponent implements OnInit {
    criteria: ICriteria;
    isSaving: boolean;

    categories: ICategorie[];

    bilanchecks: IBilanCheck[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected criteriaService: CriteriaService,
        protected categorieService: CategorieService,
        protected bilanCheckService: BilanCheckService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ criteria }) => {
            this.criteria = criteria;
        });
        this.categorieService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICategorie[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICategorie[]>) => response.body)
            )
            .subscribe((res: ICategorie[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.criteria.id !== undefined) {
            this.subscribeToSaveResponse(this.criteriaService.update(this.criteria));
        } else {
            this.subscribeToSaveResponse(this.criteriaService.create(this.criteria));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICriteria>>) {
        result.subscribe((res: HttpResponse<ICriteria>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCategorieById(index: number, item: ICategorie) {
        return item.id;
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
