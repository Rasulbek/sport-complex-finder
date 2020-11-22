import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SportsHallService } from 'app/entities/sports-hall/sports-hall.service';
import { ISportsHall, SportsHall } from 'app/shared/model/sports-hall.model';
import { FacilityStatus } from 'app/shared/model/enumerations/facility-status.model';

describe('Service Tests', () => {
  describe('SportsHall Service', () => {
    let injector: TestBed;
    let service: SportsHallService;
    let httpMock: HttpTestingController;
    let elemDefault: ISportsHall;
    let expectedResult: ISportsHall | ISportsHall[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SportsHallService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SportsHall(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        FacilityStatus.ENABLED,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SportsHall', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SportsHall()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SportsHall', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            contactPerson: 'BBBBBB',
            phone: 'BBBBBB',
            telegramNick: 'BBBBBB',
            price: 1,
            status: 'BBBBBB',
            latitude: 1,
            longitude: 1,
            address: 'BBBBBB',
            landmark: 'BBBBBB',
            ownerTelegramId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SportsHall', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            contactPerson: 'BBBBBB',
            phone: 'BBBBBB',
            telegramNick: 'BBBBBB',
            price: 1,
            status: 'BBBBBB',
            latitude: 1,
            longitude: 1,
            address: 'BBBBBB',
            landmark: 'BBBBBB',
            ownerTelegramId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SportsHall', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
