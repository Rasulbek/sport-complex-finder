import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'profile',
        loadChildren: () => import('./profile/profile.module').then(m => m.SportComplexFinderProfileModule),
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.SportComplexFinderCategoryModule),
      },
      {
        path: 'sports-hall',
        loadChildren: () => import('./sports-hall/sports-hall.module').then(m => m.SportComplexFinderSportsHallModule),
      },
      {
        path: 'image-storage',
        loadChildren: () => import('./image-storage/image-storage.module').then(m => m.SportComplexFinderImageStorageModule),
      },
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.SportComplexFinderRegionModule),
      },
      {
        path: 'city',
        loadChildren: () => import('./city/city.module').then(m => m.SportComplexFinderCityModule),
      },
      {
        path: 'administrator',
        loadChildren: () => import('./administrator/administrator.module').then(m => m.SportComplexFinderAdministratorModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SportComplexFinderEntityModule {}
