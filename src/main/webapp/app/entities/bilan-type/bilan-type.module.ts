import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    BilanTypeComponent,
    BilanTypeDetailComponent,
    BilanTypeUpdateComponent,
    BilanTypeDeletePopupComponent,
    BilanTypeDeleteDialogComponent,
    bilanTypeRoute,
    bilanTypePopupRoute
} from './';

const ENTITY_STATES = [...bilanTypeRoute, ...bilanTypePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BilanTypeComponent,
        BilanTypeDetailComponent,
        BilanTypeUpdateComponent,
        BilanTypeDeleteDialogComponent,
        BilanTypeDeletePopupComponent
    ],
    entryComponents: [BilanTypeComponent, BilanTypeUpdateComponent, BilanTypeDeleteDialogComponent, BilanTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationBilanTypeModule {}
