/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BilanTypeDetailComponent } from 'app/entities/bilan-type/bilan-type-detail.component';
import { BilanType } from 'app/shared/model/bilan-type.model';

describe('Component Tests', () => {
    describe('BilanType Management Detail Component', () => {
        let comp: BilanTypeDetailComponent;
        let fixture: ComponentFixture<BilanTypeDetailComponent>;
        const route = ({ data: of({ bilanType: new BilanType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [BilanTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BilanTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BilanTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bilanType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
