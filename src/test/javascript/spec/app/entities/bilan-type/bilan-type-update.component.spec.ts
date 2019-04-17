/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BilanTypeUpdateComponent } from 'app/entities/bilan-type/bilan-type-update.component';
import { BilanTypeService } from 'app/entities/bilan-type/bilan-type.service';
import { BilanType } from 'app/shared/model/bilan-type.model';

describe('Component Tests', () => {
    describe('BilanType Management Update Component', () => {
        let comp: BilanTypeUpdateComponent;
        let fixture: ComponentFixture<BilanTypeUpdateComponent>;
        let service: BilanTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [BilanTypeUpdateComponent]
            })
                .overrideTemplate(BilanTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BilanTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BilanTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BilanType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bilanType = entity;
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
                    const entity = new BilanType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bilanType = entity;
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
