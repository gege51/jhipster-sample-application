import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IRace } from 'app/shared/model/race.model';
import { RaceService } from './race.service';

@Component({
    selector: 'jhi-race-update',
    templateUrl: './race-update.component.html'
})
export class RaceUpdateComponent implements OnInit {
    race: IRace;
    isSaving: boolean;

    constructor(protected raceService: RaceService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ race }) => {
            this.race = race;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.race.id !== undefined) {
            this.subscribeToSaveResponse(this.raceService.update(this.race));
        } else {
            this.subscribeToSaveResponse(this.raceService.create(this.race));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRace>>) {
        result.subscribe((res: HttpResponse<IRace>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
