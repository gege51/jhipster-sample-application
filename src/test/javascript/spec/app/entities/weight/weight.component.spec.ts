/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { WeightComponent } from 'app/entities/weight/weight.component';
import { WeightService } from 'app/entities/weight/weight.service';
import { Weight } from 'app/shared/model/weight.model';

describe('Component Tests', () => {
    describe('Weight Management Component', () => {
        let comp: WeightComponent;
        let fixture: ComponentFixture<WeightComponent>;
        let service: WeightService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [WeightComponent],
                providers: []
            })
                .overrideTemplate(WeightComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WeightComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeightService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Weight(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.weights[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
