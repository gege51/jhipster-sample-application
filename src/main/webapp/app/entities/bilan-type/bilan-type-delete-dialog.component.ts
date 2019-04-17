import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBilanType } from 'app/shared/model/bilan-type.model';
import { BilanTypeService } from './bilan-type.service';

@Component({
    selector: 'jhi-bilan-type-delete-dialog',
    templateUrl: './bilan-type-delete-dialog.component.html'
})
export class BilanTypeDeleteDialogComponent {
    bilanType: IBilanType;

    constructor(
        protected bilanTypeService: BilanTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bilanTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bilanTypeListModification',
                content: 'Deleted an bilanType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bilan-type-delete-popup',
    template: ''
})
export class BilanTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bilanType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BilanTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.bilanType = bilanType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/bilan-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/bilan-type', { outlets: { popup: null } }]);
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
