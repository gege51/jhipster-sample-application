import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBilanCheck } from 'app/shared/model/bilan-check.model';
import { AccountService } from 'app/core';
import { BilanCheckService } from './bilan-check.service';

@Component({
    selector: 'jhi-bilan-check',
    templateUrl: './bilan-check.component.html'
})
export class BilanCheckComponent implements OnInit, OnDestroy {
    bilanChecks: IBilanCheck[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected bilanCheckService: BilanCheckService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.bilanCheckService
            .query()
            .pipe(
                filter((res: HttpResponse<IBilanCheck[]>) => res.ok),
                map((res: HttpResponse<IBilanCheck[]>) => res.body)
            )
            .subscribe(
                (res: IBilanCheck[]) => {
                    this.bilanChecks = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBilanChecks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBilanCheck) {
        return item.id;
    }

    registerChangeInBilanChecks() {
        this.eventSubscriber = this.eventManager.subscribe('bilanCheckListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
