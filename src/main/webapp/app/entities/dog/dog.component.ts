import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDog } from 'app/shared/model/dog.model';
import { AccountService } from 'app/core';
import { DogService } from './dog.service';

@Component({
    selector: 'jhi-dog',
    templateUrl: './dog.component.html'
})
export class DogComponent implements OnInit, OnDestroy {
    dogs: IDog[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected dogService: DogService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.dogService
            .query()
            .pipe(
                filter((res: HttpResponse<IDog[]>) => res.ok),
                map((res: HttpResponse<IDog[]>) => res.body)
            )
            .subscribe(
                (res: IDog[]) => {
                    this.dogs = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDogs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDog) {
        return item.id;
    }

    registerChangeInDogs() {
        this.eventSubscriber = this.eventManager.subscribe('dogListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
