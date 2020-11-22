import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SportComplexFinderTestModule } from '../../../test.module';
import { SportsHallDetailComponent } from 'app/entities/sports-hall/sports-hall-detail.component';
import { SportsHall } from 'app/shared/model/sports-hall.model';

describe('Component Tests', () => {
  describe('SportsHall Management Detail Component', () => {
    let comp: SportsHallDetailComponent;
    let fixture: ComponentFixture<SportsHallDetailComponent>;
    const route = ({ data: of({ sportsHall: new SportsHall(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportComplexFinderTestModule],
        declarations: [SportsHallDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SportsHallDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SportsHallDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sportsHall on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sportsHall).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
