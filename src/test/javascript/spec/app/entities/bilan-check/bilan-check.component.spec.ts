/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BilanCheckComponent } from 'app/entities/bilan-check/bilan-check.component';
import { BilanCheckService } from 'app/entities/bilan-check/bilan-check.service';
import { BilanCheck } from 'app/shared/model/bilan-check.model';

describe('Component Tests', () => {
    describe('BilanCheck Management Component', () => {
        let comp: BilanCheckComponent;
        let fixture: ComponentFixture<BilanCheckComponent>;
        let service: BilanCheckService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [BilanCheckComponent],
                providers: []
            })
                .overrideTemplate(BilanCheckComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BilanCheckComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BilanCheckService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BilanCheck(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bilanChecks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
