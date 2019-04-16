/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { RaceDetailComponent } from 'app/entities/race/race-detail.component';
import { Race } from 'app/shared/model/race.model';

describe('Component Tests', () => {
    describe('Race Management Detail Component', () => {
        let comp: RaceDetailComponent;
        let fixture: ComponentFixture<RaceDetailComponent>;
        const route = ({ data: of({ race: new Race(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [RaceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RaceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RaceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.race).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
