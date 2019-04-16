/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HealthSheetUpdateComponent } from 'app/entities/health-sheet/health-sheet-update.component';
import { HealthSheetService } from 'app/entities/health-sheet/health-sheet.service';
import { HealthSheet } from 'app/shared/model/health-sheet.model';

describe('Component Tests', () => {
    describe('HealthSheet Management Update Component', () => {
        let comp: HealthSheetUpdateComponent;
        let fixture: ComponentFixture<HealthSheetUpdateComponent>;
        let service: HealthSheetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [HealthSheetUpdateComponent]
            })
                .overrideTemplate(HealthSheetUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HealthSheetUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HealthSheetService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new HealthSheet(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.healthSheet = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new HealthSheet();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.healthSheet = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
