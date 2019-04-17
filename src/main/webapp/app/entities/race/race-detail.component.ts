import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRace } from 'app/shared/model/race.model';

@Component({
    selector: 'jhi-race-detail',
    templateUrl: './race-detail.component.html'
})
export class RaceDetailComponent implements OnInit {
    race: IRace;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ race }) => {
            this.race = race;
        });
    }

    previousState() {
        window.history.back();
    }
}
