import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICriteria } from 'app/shared/model/criteria.model';
import { CriteriaService } from './criteria.service';

@Component({
    selector: 'jhi-criteria-delete-dialog',
    templateUrl: './criteria-delete-dialog.component.html'
})
export class CriteriaDeleteDialogComponent {
    criteria: ICriteria;

    constructor(protected criteriaService: CriteriaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.criteriaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'criteriaListModification',
                content: 'Deleted an criteria'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-criteria-delete-popup',
    template: ''
})
export class CriteriaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ criteria }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CriteriaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.criteria = criteria;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/criteria', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/criteria', { outlets: { popup: null } }]);
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
