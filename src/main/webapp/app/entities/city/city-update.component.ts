import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICity, City } from 'app/shared/model/city.model';
import { CityService } from './city.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region/region.service';

@Component({
  selector: 'jhi-city-update',
  templateUrl: './city-update.component.html',
})
export class CityUpdateComponent implements OnInit {
  isSaving = false;
  regions: IRegion[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    regionId: [null, Validators.required],
  });

  constructor(
    protected cityService: CityService,
    protected regionService: RegionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ city }) => {
      this.updateForm(city);

      this.regionService.query().subscribe((res: HttpResponse<IRegion[]>) => (this.regions = res.body || []));
    });
  }

  updateForm(city: ICity): void {
    this.editForm.patchValue({
      id: city.id,
      name: city.name,
      regionId: city.regionId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const city = this.createFromForm();
    if (city.id !== undefined) {
      this.subscribeToSaveResponse(this.cityService.update(city));
    } else {
      this.subscribeToSaveResponse(this.cityService.create(city));
    }
  }

  private createFromForm(): ICity {
    return {
      ...new City(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      regionId: this.editForm.get(['regionId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICity>>): void {
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

  trackById(index: number, item: IRegion): any {
    return item.id;
  }
}
