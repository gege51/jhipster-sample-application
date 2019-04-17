import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    DogComponent,
    DogDetailComponent,
    DogUpdateComponent,
    DogDeletePopupComponent,
    DogDeleteDialogComponent,
    dogRoute,
    dogPopupRoute
} from './';

const ENTITY_STATES = [...dogRoute, ...dogPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DogComponent, DogDetailComponent, DogUpdateComponent, DogDeleteDialogComponent, DogDeletePopupComponent],
    entryComponents: [DogComponent, DogUpdateComponent, DogDeleteDialogComponent, DogDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationDogModule {}
