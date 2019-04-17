/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HealthSheetDetailComponent } from 'app/entities/health-sheet/health-sheet-detail.component';
import { HealthSheet } from 'app/shared/model/health-sheet.model';

describe('Component Tests', () => {
    describe('HealthSheet Management Detail Component', () => {
        let comp: HealthSheetDetailComponent;
        let fixture: ComponentFixture<HealthSheetDetailComponent>;
        const route = ({ data: of({ healthSheet: new HealthSheet(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [HealthSheetDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HealthSheetDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HealthSheetDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.healthSheet).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
