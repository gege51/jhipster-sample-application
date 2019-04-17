import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHealthSheet } from 'app/shared/model/health-sheet.model';

@Component({
    selector: 'jhi-health-sheet-detail',
    templateUrl: './health-sheet-detail.component.html'
})
export class HealthSheetDetailComponent implements OnInit {
    healthSheet: IHealthSheet;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ healthSheet }) => {
            this.healthSheet = healthSheet;
        });
    }

    previousState() {
        window.history.back();
    }
}
