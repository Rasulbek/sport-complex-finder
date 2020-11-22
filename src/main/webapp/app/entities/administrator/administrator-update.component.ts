import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAdministrator, Administrator } from 'app/shared/model/administrator.model';
import { AdministratorService } from './administrator.service';

@Component({
  selector: 'jhi-administrator-update',
  templateUrl: './administrator-update.component.html',
})
export class AdministratorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    chatId: [null, [Validators.required]],
    role: [],
  });

  constructor(protected administratorService: AdministratorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ administrator }) => {
      this.updateForm(administrator);
    });
  }

  updateForm(administrator: IAdministrator): void {
    this.editForm.patchValue({
      id: administrator.id,
      chatId: administrator.chatId,
      role: administrator.role,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const administrator = this.createFromForm();
    if (administrator.id !== undefined) {
      this.subscribeToSaveResponse(this.administratorService.update(administrator));
    } else {
      this.subscribeToSaveResponse(this.administratorService.create(administrator));
    }
  }

  private createFromForm(): IAdministrator {
    return {
      ...new Administrator(),
      id: this.editForm.get(['id'])!.value,
      chatId: this.editForm.get(['chatId'])!.value,
      role: this.editForm.get(['role'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdministrator>>): void {
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
