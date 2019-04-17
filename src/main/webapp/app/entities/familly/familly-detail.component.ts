import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFamilly } from 'app/shared/model/familly.model';

@Component({
    selector: 'jhi-familly-detail',
    templateUrl: './familly-detail.component.html'
})
export class FamillyDetailComponent implements OnInit {
    familly: IFamilly;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ familly }) => {
            this.familly = familly;
        });
    }

    previousState() {
        window.history.back();
    }
}
