/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DogUpdateComponent } from 'app/entities/dog/dog-update.component';
import { DogService } from 'app/entities/dog/dog.service';
import { Dog } from 'app/shared/model/dog.model';

describe('Component Tests', () => {
    describe('Dog Management Update Component', () => {
        let comp: DogUpdateComponent;
        let fixture: ComponentFixture<DogUpdateComponent>;
        let service: DogService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DogUpdateComponent]
            })
                .overrideTemplate(DogUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DogUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DogService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Dog(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dog = entity;
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
                    const entity = new Dog();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dog = entity;
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
