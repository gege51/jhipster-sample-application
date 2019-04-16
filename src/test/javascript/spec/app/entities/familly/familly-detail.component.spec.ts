/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FamillyDetailComponent } from 'app/entities/familly/familly-detail.component';
import { Familly } from 'app/shared/model/familly.model';

describe('Component Tests', () => {
    describe('Familly Management Detail Component', () => {
        let comp: FamillyDetailComponent;
        let fixture: ComponentFixture<FamillyDetailComponent>;
        const route = ({ data: of({ familly: new Familly(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [FamillyDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FamillyDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FamillyDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.familly).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
