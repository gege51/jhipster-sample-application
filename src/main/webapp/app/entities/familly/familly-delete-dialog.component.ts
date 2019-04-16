import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFamilly } from 'app/shared/model/familly.model';
import { FamillyService } from './familly.service';

@Component({
    selector: 'jhi-familly-delete-dialog',
    templateUrl: './familly-delete-dialog.component.html'
})
export class FamillyDeleteDialogComponent {
    familly: IFamilly;

    constructor(protected famillyService: FamillyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.famillyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'famillyListModification',
                content: 'Deleted an familly'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-familly-delete-popup',
    template: ''
})
export class FamillyDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ familly }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FamillyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.familly = familly;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/familly', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/familly', { outlets: { popup: null } }]);
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
