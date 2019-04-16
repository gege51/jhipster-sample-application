import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBilanCheck } from 'app/shared/model/bilan-check.model';

@Component({
    selector: 'jhi-bilan-check-detail',
    templateUrl: './bilan-check-detail.component.html'
})
export class BilanCheckDetailComponent implements OnInit {
    bilanCheck: IBilanCheck;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bilanCheck }) => {
            this.bilanCheck = bilanCheck;
        });
    }

    previousState() {
        window.history.back();
    }
}
