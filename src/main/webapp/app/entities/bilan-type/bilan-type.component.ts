import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBilanType } from 'app/shared/model/bilan-type.model';
import { AccountService } from 'app/core';
import { BilanTypeService } from './bilan-type.service';

@Component({
    selector: 'jhi-bilan-type',
    templateUrl: './bilan-type.component.html'
})
export class BilanTypeComponent implements OnInit, OnDestroy {
    bilanTypes: IBilanType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected bilanTypeService: BilanTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.bilanTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<IBilanType[]>) => res.ok),
                map((res: HttpResponse<IBilanType[]>) => res.body)
            )
            .subscribe(
                (res: IBilanType[]) => {
                    this.bilanTypes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBilanTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBilanType) {
        return item.id;
    }

    registerChangeInBilanTypes() {
        this.eventSubscriber = this.eventManager.subscribe('bilanTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
