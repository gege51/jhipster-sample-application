/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BreedingDetailComponent } from 'app/entities/breeding/breeding-detail.component';
import { Breeding } from 'app/shared/model/breeding.model';

describe('Component Tests', () => {
    describe('Breeding Management Detail Component', () => {
        let comp: BreedingDetailComponent;
        let fixture: ComponentFixture<BreedingDetailComponent>;
        const route = ({ data: of({ breeding: new Breeding(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [BreedingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BreedingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BreedingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.breeding).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
