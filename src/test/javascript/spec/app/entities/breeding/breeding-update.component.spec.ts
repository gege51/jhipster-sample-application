/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BreedingUpdateComponent } from 'app/entities/breeding/breeding-update.component';
import { BreedingService } from 'app/entities/breeding/breeding.service';
import { Breeding } from 'app/shared/model/breeding.model';

describe('Component Tests', () => {
    describe('Breeding Management Update Component', () => {
        let comp: BreedingUpdateComponent;
        let fixture: ComponentFixture<BreedingUpdateComponent>;
        let service: BreedingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [BreedingUpdateComponent]
            })
                .overrideTemplate(BreedingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BreedingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BreedingService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Breeding(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.breeding = entity;
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
                    const entity = new Breeding();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.breeding = entity;
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
