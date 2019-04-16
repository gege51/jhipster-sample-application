/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { RaceComponent } from 'app/entities/race/race.component';
import { RaceService } from 'app/entities/race/race.service';
import { Race } from 'app/shared/model/race.model';

describe('Component Tests', () => {
    describe('Race Management Component', () => {
        let comp: RaceComponent;
        let fixture: ComponentFixture<RaceComponent>;
        let service: RaceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [RaceComponent],
                providers: []
            })
                .overrideTemplate(RaceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RaceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RaceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Race(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.races[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
