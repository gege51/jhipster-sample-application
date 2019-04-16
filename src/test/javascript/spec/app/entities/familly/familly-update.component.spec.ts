/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FamillyUpdateComponent } from 'app/entities/familly/familly-update.component';
import { FamillyService } from 'app/entities/familly/familly.service';
import { Familly } from 'app/shared/model/familly.model';

describe('Component Tests', () => {
    describe('Familly Management Update Component', () => {
        let comp: FamillyUpdateComponent;
        let fixture: ComponentFixture<FamillyUpdateComponent>;
        let service: FamillyService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [FamillyUpdateComponent]
            })
                .overrideTemplate(FamillyUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FamillyUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FamillyService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Familly(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.familly = entity;
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
                    const entity = new Familly();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.familly = entity;
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
