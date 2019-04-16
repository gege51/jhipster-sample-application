/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { RaceUpdateComponent } from 'app/entities/race/race-update.component';
import { RaceService } from 'app/entities/race/race.service';
import { Race } from 'app/shared/model/race.model';

describe('Component Tests', () => {
    describe('Race Management Update Component', () => {
        let comp: RaceUpdateComponent;
        let fixture: ComponentFixture<RaceUpdateComponent>;
        let service: RaceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [RaceUpdateComponent]
            })
                .overrideTemplate(RaceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RaceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RaceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Race(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.race = entity;
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
                    const entity = new Race();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.race = entity;
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
