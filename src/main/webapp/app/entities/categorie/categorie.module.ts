import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    CategorieComponent,
    CategorieDetailComponent,
    CategorieUpdateComponent,
    CategorieDeletePopupComponent,
    CategorieDeleteDialogComponent,
    categorieRoute,
    categoriePopupRoute
} from './';

const ENTITY_STATES = [...categorieRoute, ...categoriePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CategorieComponent,
        CategorieDetailComponent,
        CategorieUpdateComponent,
        CategorieDeleteDialogComponent,
        CategorieDeletePopupComponent
    ],
    entryComponents: [CategorieComponent, CategorieUpdateComponent, CategorieDeleteDialogComponent, CategorieDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationCategorieModule {}
