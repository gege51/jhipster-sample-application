/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BreedingComponent } from 'app/entities/breeding/breeding.component';
import { BreedingService } from 'app/entities/breeding/breeding.service';
import { Breeding } from 'app/shared/model/breeding.model';

describe('Component Tests', () => {
    describe('Breeding Management Component', () => {
        let comp: BreedingComponent;
        let fixture: ComponentFixture<BreedingComponent>;
        let service: BreedingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [BreedingComponent],
                providers: []
            })
                .overrideTemplate(BreedingComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BreedingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BreedingService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Breeding(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.breedings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
