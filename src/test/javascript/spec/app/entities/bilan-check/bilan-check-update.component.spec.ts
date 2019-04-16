/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BilanCheckUpdateComponent } from 'app/entities/bilan-check/bilan-check-update.component';
import { BilanCheckService } from 'app/entities/bilan-check/bilan-check.service';
import { BilanCheck } from 'app/shared/model/bilan-check.model';

describe('Component Tests', () => {
    describe('BilanCheck Management Update Component', () => {
        let comp: BilanCheckUpdateComponent;
        let fixture: ComponentFixture<BilanCheckUpdateComponent>;
        let service: BilanCheckService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [BilanCheckUpdateComponent]
            })
                .overrideTemplate(BilanCheckUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BilanCheckUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BilanCheckService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BilanCheck(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bilanCheck = entity;
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
                    const entity = new BilanCheck();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bilanCheck = entity;
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
