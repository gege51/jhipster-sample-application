/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CenterComponent } from 'app/entities/center/center.component';
import { CenterService } from 'app/entities/center/center.service';
import { Center } from 'app/shared/model/center.model';

describe('Component Tests', () => {
    describe('Center Management Component', () => {
        let comp: CenterComponent;
        let fixture: ComponentFixture<CenterComponent>;
        let service: CenterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [CenterComponent],
                providers: []
            })
                .overrideTemplate(CenterComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CenterComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CenterService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Center(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.centers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
