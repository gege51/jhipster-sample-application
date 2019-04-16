import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRace } from 'app/shared/model/race.model';
import { AccountService } from 'app/core';
import { RaceService } from './race.service';

@Component({
    selector: 'jhi-race',
    templateUrl: './race.component.html'
})
export class RaceComponent implements OnInit, OnDestroy {
    races: IRace[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected raceService: RaceService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.raceService
            .query()
            .pipe(
                filter((res: HttpResponse<IRace[]>) => res.ok),
                map((res: HttpResponse<IRace[]>) => res.body)
            )
            .subscribe(
                (res: IRace[]) => {
                    this.races = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRaces();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRace) {
        return item.id;
    }

    registerChangeInRaces() {
        this.eventSubscriber = this.eventManager.subscribe('raceListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
