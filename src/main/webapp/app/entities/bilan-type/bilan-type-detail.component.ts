import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBilanType } from 'app/shared/model/bilan-type.model';

@Component({
    selector: 'jhi-bilan-type-detail',
    templateUrl: './bilan-type-detail.component.html'
})
export class BilanTypeDetailComponent implements OnInit {
    bilanType: IBilanType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bilanType }) => {
            this.bilanType = bilanType;
        });
    }

    previousState() {
        window.history.back();
    }
}
