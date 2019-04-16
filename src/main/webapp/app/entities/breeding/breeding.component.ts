import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBreeding } from 'app/shared/model/breeding.model';
import { AccountService } from 'app/core';
import { BreedingService } from './breeding.service';

@Component({
    selector: 'jhi-breeding',
    templateUrl: './breeding.component.html'
})
export class BreedingComponent implements OnInit, OnDestroy {
    breedings: IBreeding[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected breedingService: BreedingService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.breedingService
            .query()
            .pipe(
                filter((res: HttpResponse<IBreeding[]>) => res.ok),
                map((res: HttpResponse<IBreeding[]>) => res.body)
            )
            .subscribe(
                (res: IBreeding[]) => {
                    this.breedings = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBreedings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBreeding) {
        return item.id;
    }

    registerChangeInBreedings() {
        this.eventSubscriber = this.eventManager.subscribe('breedingListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
