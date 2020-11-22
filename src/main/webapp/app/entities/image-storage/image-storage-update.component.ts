import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IImageStorage, ImageStorage } from 'app/shared/model/image-storage.model';
import { ImageStorageService } from './image-storage.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-image-storage-update',
  templateUrl: './image-storage-update.component.html',
})
export class ImageStorageUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    image: [null, [Validators.required]],
    imageContentType: [],
    sportsHallId: [],
    isPrimary: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected imageStorageService: ImageStorageService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ imageStorage }) => {
      this.updateForm(imageStorage);
    });
  }

  updateForm(imageStorage: IImageStorage): void {
    this.editForm.patchValue({
      id: imageStorage.id,
      image: imageStorage.image,
      imageContentType: imageStorage.imageContentType,
      sportsHallId: imageStorage.sportsHallId,
      isPrimary: imageStorage.isPrimary,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('sportComplexFinderApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const imageStorage = this.createFromForm();
    if (imageStorage.id !== undefined) {
      this.subscribeToSaveResponse(this.imageStorageService.update(imageStorage));
    } else {
      this.subscribeToSaveResponse(this.imageStorageService.create(imageStorage));
    }
  }

  private createFromForm(): IImageStorage {
    return {
      ...new ImageStorage(),
      id: this.editForm.get(['id'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      sportsHallId: this.editForm.get(['sportsHallId'])!.value,
      isPrimary: this.editForm.get(['isPrimary'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImageStorage>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
