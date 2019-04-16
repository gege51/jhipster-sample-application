import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'dog',
                loadChildren: './dog/dog.module#JhipsterSampleApplicationDogModule'
            },
            {
                path: 'race',
                loadChildren: './race/race.module#JhipsterSampleApplicationRaceModule'
            },
            {
                path: 'color',
                loadChildren: './color/color.module#JhipsterSampleApplicationColorModule'
            },
            {
                path: 'weight',
                loadChildren: './weight/weight.module#JhipsterSampleApplicationWeightModule'
            },
            {
                path: 'center',
                loadChildren: './center/center.module#JhipsterSampleApplicationCenterModule'
            },
            {
                path: 'breeding',
                loadChildren: './breeding/breeding.module#JhipsterSampleApplicationBreedingModule'
            },
            {
                path: 'familly',
                loadChildren: './familly/familly.module#JhipsterSampleApplicationFamillyModule'
            },
            {
                path: 'location',
                loadChildren: './location/location.module#JhipsterSampleApplicationLocationModule'
            },
            {
                path: 'health-sheet',
                loadChildren: './health-sheet/health-sheet.module#JhipsterSampleApplicationHealthSheetModule'
            },
            {
                path: 'categorie',
                loadChildren: './categorie/categorie.module#JhipsterSampleApplicationCategorieModule'
            },
            {
                path: 'criteria',
                loadChildren: './criteria/criteria.module#JhipsterSampleApplicationCriteriaModule'
            },
            {
                path: 'bilan-type',
                loadChildren: './bilan-type/bilan-type.module#JhipsterSampleApplicationBilanTypeModule'
            },
            {
                path: 'bilan-check',
                loadChildren: './bilan-check/bilan-check.module#JhipsterSampleApplicationBilanCheckModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
