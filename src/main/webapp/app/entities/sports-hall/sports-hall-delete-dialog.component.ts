import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISportsHall } from 'app/shared/model/sports-hall.model';
import { SportsHallService } from './sports-hall.service';

@Component({
  templateUrl: './sports-hall-delete-dialog.component.html',
})
export class SportsHallDeleteDialogComponent {
  sportsHall?: ISportsHall;

  constructor(
    protected sportsHallService: SportsHallService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sportsHallService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sportsHallListModification');
      this.activeModal.close();
    });
  }
}
