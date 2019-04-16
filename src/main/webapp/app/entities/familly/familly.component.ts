import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFamilly } from 'app/shared/model/familly.model';
import { AccountService } from 'app/core';
import { FamillyService } from './familly.service';

@Component({
    selector: 'jhi-familly',
    templateUrl: './familly.component.html'
})
export class FamillyComponent implements OnInit, OnDestroy {
    famillies: IFamilly[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected famillyService: FamillyService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.famillyService
            .query()
            .pipe(
                filter((res: HttpResponse<IFamilly[]>) => res.ok),
                map((res: HttpResponse<IFamilly[]>) => res.body)
            )
            .subscribe(
                (res: IFamilly[]) => {
                    this.famillies = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFamillies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFamilly) {
        return item.id;
    }

    registerChangeInFamillies() {
        this.eventSubscriber = this.eventManager.subscribe('famillyListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
