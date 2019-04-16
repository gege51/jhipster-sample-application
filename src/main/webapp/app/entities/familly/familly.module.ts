import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    FamillyComponent,
    FamillyDetailComponent,
    FamillyUpdateComponent,
    FamillyDeletePopupComponent,
    FamillyDeleteDialogComponent,
    famillyRoute,
    famillyPopupRoute
} from './';

const ENTITY_STATES = [...famillyRoute, ...famillyPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FamillyComponent,
        FamillyDetailComponent,
        FamillyUpdateComponent,
        FamillyDeleteDialogComponent,
        FamillyDeletePopupComponent
    ],
    entryComponents: [FamillyComponent, FamillyUpdateComponent, FamillyDeleteDialogComponent, FamillyDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationFamillyModule {}
