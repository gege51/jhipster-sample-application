import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IWeight } from 'app/shared/model/weight.model';
import { AccountService } from 'app/core';
import { WeightService } from './weight.service';

@Component({
    selector: 'jhi-weight',
    templateUrl: './weight.component.html'
})
export class WeightComponent implements OnInit, OnDestroy {
    weights: IWeight[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected weightService: WeightService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.weightService
            .query()
            .pipe(
                filter((res: HttpResponse<IWeight[]>) => res.ok),
                map((res: HttpResponse<IWeight[]>) => res.body)
            )
            .subscribe(
                (res: IWeight[]) => {
                    this.weights = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInWeights();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IWeight) {
        return item.id;
    }

    registerChangeInWeights() {
        this.eventSubscriber = this.eventManager.subscribe('weightListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
