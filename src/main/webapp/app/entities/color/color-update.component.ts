import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IColor } from 'app/shared/model/color.model';
import { ColorService } from './color.service';

@Component({
    selector: 'jhi-color-update',
    templateUrl: './color-update.component.html'
})
export class ColorUpdateComponent implements OnInit {
    color: IColor;
    isSaving: boolean;

    constructor(protected colorService: ColorService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ color }) => {
            this.color = color;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.color.id !== undefined) {
            this.subscribeToSaveResponse(this.colorService.update(this.color));
        } else {
            this.subscribeToSaveResponse(this.colorService.create(this.color));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IColor>>) {
        result.subscribe((res: HttpResponse<IColor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
