import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportComplexFinderSharedModule } from 'app/shared/shared.module';
import { ImageStorageComponent } from './image-storage.component';
import { ImageStorageDetailComponent } from './image-storage-detail.component';
import { ImageStorageUpdateComponent } from './image-storage-update.component';
import { ImageStorageDeleteDialogComponent } from './image-storage-delete-dialog.component';
import { imageStorageRoute } from './image-storage.route';

@NgModule({
  imports: [SportComplexFinderSharedModule, RouterModule.forChild(imageStorageRoute)],
  declarations: [ImageStorageComponent, ImageStorageDetailComponent, ImageStorageUpdateComponent, ImageStorageDeleteDialogComponent],
  entryComponents: [ImageStorageDeleteDialogComponent],
})
export class SportComplexFinderImageStorageModule {}
