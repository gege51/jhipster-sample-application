import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    BilanCheckComponent,
    BilanCheckDetailComponent,
    BilanCheckUpdateComponent,
    BilanCheckDeletePopupComponent,
    BilanCheckDeleteDialogComponent,
    bilanCheckRoute,
    bilanCheckPopupRoute
} from './';

const ENTITY_STATES = [...bilanCheckRoute, ...bilanCheckPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BilanCheckComponent,
        BilanCheckDetailComponent,
        BilanCheckUpdateComponent,
        BilanCheckDeleteDialogComponent,
        BilanCheckDeletePopupComponent
    ],
    entryComponents: [BilanCheckComponent, BilanCheckUpdateComponent, BilanCheckDeleteDialogComponent, BilanCheckDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationBilanCheckModule {}
