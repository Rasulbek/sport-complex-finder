import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IImageStorage } from 'app/shared/model/image-storage.model';

@Component({
  selector: 'jhi-image-storage-detail',
  templateUrl: './image-storage-detail.component.html',
})
export class ImageStorageDetailComponent implements OnInit {
  imageStorage: IImageStorage | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ imageStorage }) => (this.imageStorage = imageStorage));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
