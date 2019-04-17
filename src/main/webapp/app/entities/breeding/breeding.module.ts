import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    BreedingComponent,
    BreedingDetailComponent,
    BreedingUpdateComponent,
    BreedingDeletePopupComponent,
    BreedingDeleteDialogComponent,
    breedingRoute,
    breedingPopupRoute
} from './';

const ENTITY_STATES = [...breedingRoute, ...breedingPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BreedingComponent,
        BreedingDetailComponent,
        BreedingUpdateComponent,
        BreedingDeleteDialogComponent,
        BreedingDeletePopupComponent
    ],
    entryComponents: [BreedingComponent, BreedingUpdateComponent, BreedingDeleteDialogComponent, BreedingDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationBreedingModule {}
