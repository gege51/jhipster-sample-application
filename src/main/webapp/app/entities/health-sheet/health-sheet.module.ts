import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    HealthSheetComponent,
    HealthSheetDetailComponent,
    HealthSheetUpdateComponent,
    HealthSheetDeletePopupComponent,
    HealthSheetDeleteDialogComponent,
    healthSheetRoute,
    healthSheetPopupRoute
} from './';

const ENTITY_STATES = [...healthSheetRoute, ...healthSheetPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HealthSheetComponent,
        HealthSheetDetailComponent,
        HealthSheetUpdateComponent,
        HealthSheetDeleteDialogComponent,
        HealthSheetDeletePopupComponent
    ],
    entryComponents: [HealthSheetComponent, HealthSheetUpdateComponent, HealthSheetDeleteDialogComponent, HealthSheetDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationHealthSheetModule {}
