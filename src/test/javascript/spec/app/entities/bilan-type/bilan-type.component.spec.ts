/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BilanTypeComponent } from 'app/entities/bilan-type/bilan-type.component';
import { BilanTypeService } from 'app/entities/bilan-type/bilan-type.service';
import { BilanType } from 'app/shared/model/bilan-type.model';

describe('Component Tests', () => {
    describe('BilanType Management Component', () => {
        let comp: BilanTypeComponent;
        let fixture: ComponentFixture<BilanTypeComponent>;
        let service: BilanTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [BilanTypeComponent],
                providers: []
            })
                .overrideTemplate(BilanTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BilanTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BilanTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BilanType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bilanTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
