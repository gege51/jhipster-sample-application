import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICriteria } from 'app/shared/model/criteria.model';
import { AccountService } from 'app/core';
import { CriteriaService } from './criteria.service';

@Component({
    selector: 'jhi-criteria',
    templateUrl: './criteria.component.html'
})
export class CriteriaComponent implements OnInit, OnDestroy {
    criteria: ICriteria[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected criteriaService: CriteriaService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.criteriaService
            .query()
            .pipe(
                filter((res: HttpResponse<ICriteria[]>) => res.ok),
                map((res: HttpResponse<ICriteria[]>) => res.body)
            )
            .subscribe(
                (res: ICriteria[]) => {
                    this.criteria = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCriteria();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICriteria) {
        return item.id;
    }

    registerChangeInCriteria() {
        this.eventSubscriber = this.eventManager.subscribe('criteriaListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
