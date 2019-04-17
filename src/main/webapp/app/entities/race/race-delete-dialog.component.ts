import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRace } from 'app/shared/model/race.model';
import { RaceService } from './race.service';

@Component({
    selector: 'jhi-race-delete-dialog',
    templateUrl: './race-delete-dialog.component.html'
})
export class RaceDeleteDialogComponent {
    race: IRace;

    constructor(protected raceService: RaceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.raceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'raceListModification',
                content: 'Deleted an race'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-race-delete-popup',
    template: ''
})
export class RaceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ race }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RaceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.race = race;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/race', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/race', { outlets: { popup: null } }]);
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
