import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProfile, Profile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';
import { ICity } from 'app/shared/model/city.model';
import { CityService } from 'app/entities/city/city.service';

@Component({
  selector: 'jhi-profile-update',
  templateUrl: './profile-update.component.html',
})
export class ProfileUpdateComponent implements OnInit {
  isSaving = false;
  cities: ICity[] = [];

  editForm = this.fb.group({
    id: [],
    phone: [null, [Validators.maxLength(15)]],
    chatId: [],
    userName: [null, [Validators.maxLength(100)]],
    fullName: [],
    chosenLang: [null, [Validators.maxLength(2)]],
    status: [null, [Validators.required]],
    cityId: [],
  });

  constructor(
    protected profileService: ProfileService,
    protected cityService: CityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profile }) => {
      this.updateForm(profile);

      this.cityService.query().subscribe((res: HttpResponse<ICity[]>) => (this.cities = res.body || []));
    });
  }

  updateForm(profile: IProfile): void {
    this.editForm.patchValue({
      id: profile.id,
      phone: profile.phone,
      chatId: profile.chatId,
      userName: profile.userName,
      fullName: profile.fullName,
      chosenLang: profile.chosenLang,
      status: profile.status,
      cityId: profile.cityId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const profile = this.createFromForm();
    if (profile.id !== undefined) {
      this.subscribeToSaveResponse(this.profileService.update(profile));
    } else {
      this.subscribeToSaveResponse(this.profileService.create(profile));
    }
  }

  private createFromForm(): IProfile {
    return {
      ...new Profile(),
      id: this.editForm.get(['id'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      chatId: this.editForm.get(['chatId'])!.value,
      userName: this.editForm.get(['userName'])!.value,
      fullName: this.editForm.get(['fullName'])!.value,
      chosenLang: this.editForm.get(['chosenLang'])!.value,
      status: this.editForm.get(['status'])!.value,
      cityId: this.editForm.get(['cityId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>): void {
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

  trackById(index: number, item: ICity): any {
    return item.id;
  }
}
