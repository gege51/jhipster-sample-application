/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CriteriaComponent } from 'app/entities/criteria/criteria.component';
import { CriteriaService } from 'app/entities/criteria/criteria.service';
import { Criteria } from 'app/shared/model/criteria.model';

describe('Component Tests', () => {
    describe('Criteria Management Component', () => {
        let comp: CriteriaComponent;
        let fixture: ComponentFixture<CriteriaComponent>;
        let service: CriteriaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [CriteriaComponent],
                providers: []
            })
                .overrideTemplate(CriteriaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CriteriaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CriteriaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Criteria(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.criteria[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
