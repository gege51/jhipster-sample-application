import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBilanCheck } from 'app/shared/model/bilan-check.model';
import { BilanCheckService } from './bilan-check.service';

@Component({
    selector: 'jhi-bilan-check-delete-dialog',
    templateUrl: './bilan-check-delete-dialog.component.html'
})
export class BilanCheckDeleteDialogComponent {
    bilanCheck: IBilanCheck;

    constructor(
        protected bilanCheckService: BilanCheckService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bilanCheckService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bilanCheckListModification',
                content: 'Deleted an bilanCheck'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bilan-check-delete-popup',
    template: ''
})
export class BilanCheckDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bilanCheck }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BilanCheckDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.bilanCheck = bilanCheck;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/bilan-check', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/bilan-check', { outlets: { popup: null } }]);
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
