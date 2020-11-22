import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISportsHall, SportsHall } from 'app/shared/model/sports-hall.model';
import { SportsHallService } from './sports-hall.service';
import { SportsHallComponent } from './sports-hall.component';
import { SportsHallDetailComponent } from './sports-hall-detail.component';
import { SportsHallUpdateComponent } from './sports-hall-update.component';

@Injectable({ providedIn: 'root' })
export class SportsHallResolve implements Resolve<ISportsHall> {
  constructor(private service: SportsHallService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISportsHall> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sportsHall: HttpResponse<SportsHall>) => {
          if (sportsHall.body) {
            return of(sportsHall.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SportsHall());
  }
}

export const sportsHallRoute: Routes = [
  {
    path: '',
    component: SportsHallComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sportComplexFinderApp.sportsHall.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SportsHallDetailComponent,
    resolve: {
      sportsHall: SportsHallResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sportComplexFinderApp.sportsHall.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SportsHallUpdateComponent,
    resolve: {
      sportsHall: SportsHallResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sportComplexFinderApp.sportsHall.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SportsHallUpdateComponent,
    resolve: {
      sportsHall: SportsHallResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sportComplexFinderApp.sportsHall.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
