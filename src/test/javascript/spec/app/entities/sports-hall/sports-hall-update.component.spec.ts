import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SportComplexFinderTestModule } from '../../../test.module';
import { SportsHallUpdateComponent } from 'app/entities/sports-hall/sports-hall-update.component';
import { SportsHallService } from 'app/entities/sports-hall/sports-hall.service';
import { SportsHall } from 'app/shared/model/sports-hall.model';

describe('Component Tests', () => {
  describe('SportsHall Management Update Component', () => {
    let comp: SportsHallUpdateComponent;
    let fixture: ComponentFixture<SportsHallUpdateComponent>;
    let service: SportsHallService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportComplexFinderTestModule],
        declarations: [SportsHallUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SportsHallUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SportsHallUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SportsHallService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SportsHall(123);
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
        const entity = new SportsHall();
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
