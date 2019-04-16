/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BilanCheckDetailComponent } from 'app/entities/bilan-check/bilan-check-detail.component';
import { BilanCheck } from 'app/shared/model/bilan-check.model';

describe('Component Tests', () => {
    describe('BilanCheck Management Detail Component', () => {
        let comp: BilanCheckDetailComponent;
        let fixture: ComponentFixture<BilanCheckDetailComponent>;
        const route = ({ data: of({ bilanCheck: new BilanCheck(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [BilanCheckDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BilanCheckDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BilanCheckDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bilanCheck).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
