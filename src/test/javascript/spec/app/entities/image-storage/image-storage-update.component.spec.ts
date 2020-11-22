import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SportComplexFinderTestModule } from '../../../test.module';
import { ImageStorageUpdateComponent } from 'app/entities/image-storage/image-storage-update.component';
import { ImageStorageService } from 'app/entities/image-storage/image-storage.service';
import { ImageStorage } from 'app/shared/model/image-storage.model';

describe('Component Tests', () => {
  describe('ImageStorage Management Update Component', () => {
    let comp: ImageStorageUpdateComponent;
    let fixture: ComponentFixture<ImageStorageUpdateComponent>;
    let service: ImageStorageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportComplexFinderTestModule],
        declarations: [ImageStorageUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ImageStorageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImageStorageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImageStorageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ImageStorage(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ImageStorage();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
