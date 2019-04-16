/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DogComponent } from 'app/entities/dog/dog.component';
import { DogService } from 'app/entities/dog/dog.service';
import { Dog } from 'app/shared/model/dog.model';

describe('Component Tests', () => {
    describe('Dog Management Component', () => {
        let comp: DogComponent;
        let fixture: ComponentFixture<DogComponent>;
        let service: DogService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DogComponent],
                providers: []
            })
                .overrideTemplate(DogComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DogService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Dog(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
