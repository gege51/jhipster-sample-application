import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICenter } from 'app/shared/model/center.model';
import { AccountService } from 'app/core';
import { CenterService } from './center.service';

@Component({
    selector: 'jhi-center',
    templateUrl: './center.component.html'
})
export class CenterComponent implements OnInit, OnDestroy {
    centers: ICenter[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected centerService: CenterService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.centerService
            .query()
            .pipe(
                filter((res: HttpResponse<ICenter[]>) => res.ok),
                map((res: HttpResponse<ICenter[]>) => res.body)
            )
            .subscribe(
                (res: ICenter[]) => {
                    this.centers = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCenters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICenter) {
        return item.id;
    }

    registerChangeInCenters() {
        this.eventSubscriber = this.eventManager.subscribe('centerListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
