/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HealthSheetDeleteDialogComponent } from 'app/entities/health-sheet/health-sheet-delete-dialog.component';
import { HealthSheetService } from 'app/entities/health-sheet/health-sheet.service';

describe('Component Tests', () => {
    describe('HealthSheet Management Delete Component', () => {
        let comp: HealthSheetDeleteDialogComponent;
        let fixture: ComponentFixture<HealthSheetDeleteDialogComponent>;
        let service: HealthSheetService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [HealthSheetDeleteDialogComponent]
            })
                .overrideTemplate(HealthSheetDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HealthSheetDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HealthSheetService);
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
