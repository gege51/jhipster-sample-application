/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BilanCheckDeleteDialogComponent } from 'app/entities/bilan-check/bilan-check-delete-dialog.component';
import { BilanCheckService } from 'app/entities/bilan-check/bilan-check.service';

describe('Component Tests', () => {
    describe('BilanCheck Management Delete Component', () => {
        let comp: BilanCheckDeleteDialogComponent;
        let fixture: ComponentFixture<BilanCheckDeleteDialogComponent>;
        let service: BilanCheckService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [BilanCheckDeleteDialogComponent]
            })
                .overrideTemplate(BilanCheckDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BilanCheckDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BilanCheckService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
