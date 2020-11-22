import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISportsHall, SportsHall } from 'app/shared/model/sports-hall.model';
import { SportsHallService } from './sports-hall.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';
import { ICity } from 'app/shared/model/city.model';
import { CityService } from 'app/entities/city/city.service';

type SelectableEntity = ICategory | ICity;

@Component({
  selector: 'jhi-sports-hall-update',
  templateUrl: './sports-hall-update.component.html',
})
export class SportsHallUpdateComponent implements OnInit {
  isSaving = false;
  categories: ICategory[] = [];
  cities: ICity[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.maxLength(400)]],
    contactPerson: [null, [Validators.required]],
    phone: [],
    telegramNick: [],
    price: [null, [Validators.required]],
    status: [null, [Validators.required]],
    latitude: [],
    longitude: [],
    address: [],
    landmark: [],
    ownerTelegramId: [],
    categoryId: [null, Validators.required],
    cityId: [null, Validators.required],
  });

  constructor(
    protected sportsHallService: SportsHallService,
    protected categoryService: CategoryService,
    protected cityService: CityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sportsHall }) => {
      this.updateForm(sportsHall);

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));

      this.cityService.query().subscribe((res: HttpResponse<ICity[]>) => (this.cities = res.body || []));
    });
  }

  updateForm(sportsHall: ISportsHall): void {
    this.editForm.patchValue({
      id: sportsHall.id,
      name: sportsHall.name,
      description: sportsHall.description,
      contactPerson: sportsHall.contactPerson,
      phone: sportsHall.phone,
      telegramNick: sportsHall.telegramNick,
      price: sportsHall.price,
      status: sportsHall.status,
      latitude: sportsHall.latitude,
      longitude: sportsHall.longitude,
      address: sportsHall.address,
      landmark: sportsHall.landmark,
      ownerTelegramId: sportsHall.ownerTelegramId,
      categoryId: sportsHall.categoryId,
      cityId: sportsHall.cityId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sportsHall = this.createFromForm();
    if (sportsHall.id !== undefined) {
      this.subscribeToSaveResponse(this.sportsHallService.update(sportsHall));
    } else {
      this.subscribeToSaveResponse(this.sportsHallService.create(sportsHall));
    }
  }

  private createFromForm(): ISportsHall {
    return {
      ...new SportsHall(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      contactPerson: this.editForm.get(['contactPerson'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      telegramNick: this.editForm.get(['telegramNick'])!.value,
      price: this.editForm.get(['price'])!.value,
      status: this.editForm.get(['status'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      address: this.editForm.get(['address'])!.value,
      landmark: this.editForm.get(['landmark'])!.value,
      ownerTelegramId: this.editForm.get(['ownerTelegramId'])!.value,
      categoryId: this.editForm.get(['categoryId'])!.value,
      cityId: this.editForm.get(['cityId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISportsHall>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
