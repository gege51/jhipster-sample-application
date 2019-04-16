import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHealthSheet } from 'app/shared/model/health-sheet.model';
import { HealthSheetService } from './health-sheet.service';

@Component({
    selector: 'jhi-health-sheet-delete-dialog',
    templateUrl: './health-sheet-delete-dialog.component.html'
})
export class HealthSheetDeleteDialogComponent {
    healthSheet: IHealthSheet;

    constructor(
        protected healthSheetService: HealthSheetService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.healthSheetService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'healthSheetListModification',
                content: 'Deleted an healthSheet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-health-sheet-delete-popup',
    template: ''
})
export class HealthSheetDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ healthSheet }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HealthSheetDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.healthSheet = healthSheet;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/health-sheet', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/health-sheet', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
