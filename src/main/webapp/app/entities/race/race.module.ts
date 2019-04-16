import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    RaceComponent,
    RaceDetailComponent,
    RaceUpdateComponent,
    RaceDeletePopupComponent,
    RaceDeleteDialogComponent,
    raceRoute,
    racePopupRoute
} from './';

const ENTITY_STATES = [...raceRoute, ...racePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RaceComponent, RaceDetailComponent, RaceUpdateComponent, RaceDeleteDialogComponent, RaceDeletePopupComponent],
    entryComponents: [RaceComponent, RaceUpdateComponent, RaceDeleteDialogComponent, RaceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationRaceModule {}
