import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImageStorage } from 'app/shared/model/image-storage.model';
import { ImageStorageService } from './image-storage.service';

@Component({
  templateUrl: './image-storage-delete-dialog.component.html',
})
export class ImageStorageDeleteDialogComponent {
  imageStorage?: IImageStorage;

  constructor(
    protected imageStorageService: ImageStorageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.imageStorageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('imageStorageListModification');
      this.activeModal.close();
    });
  }
}
