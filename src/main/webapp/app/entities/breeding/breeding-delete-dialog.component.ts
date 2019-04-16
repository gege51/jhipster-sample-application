import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBreeding } from 'app/shared/model/breeding.model';
import { BreedingService } from './breeding.service';

@Component({
    selector: 'jhi-breeding-delete-dialog',
    templateUrl: './breeding-delete-dialog.component.html'
})
export class BreedingDeleteDialogComponent {
    breeding: IBreeding;

    constructor(protected breedingService: BreedingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.breedingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'breedingListModification',
                content: 'Deleted an breeding'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-breeding-delete-popup',
    template: ''
})
export class BreedingDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ breeding }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BreedingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.breeding = breeding;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/breeding', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/breeding', { outlets: { popup: null } }]);
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
