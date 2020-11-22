import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IImageStorage, ImageStorage } from 'app/shared/model/image-storage.model';
import { ImageStorageService } from './image-storage.service';
import { ImageStorageComponent } from './image-storage.component';
import { ImageStorageDetailComponent } from './image-storage-detail.component';
import { ImageStorageUpdateComponent } from './image-storage-update.component';

@Injectable({ providedIn: 'root' })
export class ImageStorageResolve implements Resolve<IImageStorage> {
  constructor(private service: ImageStorageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IImageStorage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((imageStorage: HttpResponse<ImageStorage>) => {
          if (imageStorage.body) {
            return of(imageStorage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ImageStorage());
  }
}

export const imageStorageRoute: Routes = [
  {
    path: '',
    component: ImageStorageComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sportComplexFinderApp.imageStorage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ImageStorageDetailComponent,
    resolve: {
      imageStorage: ImageStorageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sportComplexFinderApp.imageStorage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ImageStorageUpdateComponent,
    resolve: {
      imageStorage: ImageStorageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sportComplexFinderApp.imageStorage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ImageStorageUpdateComponent,
    resolve: {
      imageStorage: ImageStorageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sportComplexFinderApp.imageStorage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
