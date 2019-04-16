import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBreeding } from 'app/shared/model/breeding.model';

@Component({
    selector: 'jhi-breeding-detail',
    templateUrl: './breeding-detail.component.html'
})
export class BreedingDetailComponent implements OnInit {
    breeding: IBreeding;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ breeding }) => {
            this.breeding = breeding;
        });
    }

    previousState() {
        window.history.back();
    }
}
