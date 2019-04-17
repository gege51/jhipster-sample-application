import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHealthSheet } from 'app/shared/model/health-sheet.model';
import { AccountService } from 'app/core';
import { HealthSheetService } from './health-sheet.service';

@Component({
    selector: 'jhi-health-sheet',
    templateUrl: './health-sheet.component.html'
})
export class HealthSheetComponent implements OnInit, OnDestroy {
    healthSheets: IHealthSheet[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected healthSheetService: HealthSheetService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.healthSheetService
            .query()
            .pipe(
                filter((res: HttpResponse<IHealthSheet[]>) => res.ok),
                map((res: HttpResponse<IHealthSheet[]>) => res.body)
            )
            .subscribe(
                (res: IHealthSheet[]) => {
                    this.healthSheets = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHealthSheets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHealthSheet) {
        return item.id;
    }

    registerChangeInHealthSheets() {
        this.eventSubscriber = this.eventManager.subscribe('healthSheetListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
