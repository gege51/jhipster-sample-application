/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HealthSheetComponent } from 'app/entities/health-sheet/health-sheet.component';
import { HealthSheetService } from 'app/entities/health-sheet/health-sheet.service';
import { HealthSheet } from 'app/shared/model/health-sheet.model';

describe('Component Tests', () => {
    describe('HealthSheet Management Component', () => {
        let comp: HealthSheetComponent;
        let fixture: ComponentFixture<HealthSheetComponent>;
        let service: HealthSheetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [HealthSheetComponent],
                providers: []
            })
                .overrideTemplate(HealthSheetComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HealthSheetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HealthSheetService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new HealthSheet(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.healthSheets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
