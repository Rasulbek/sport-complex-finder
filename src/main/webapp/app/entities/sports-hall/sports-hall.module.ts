import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportComplexFinderSharedModule } from 'app/shared/shared.module';
import { SportsHallComponent } from './sports-hall.component';
import { SportsHallDetailComponent } from './sports-hall-detail.component';
import { SportsHallUpdateComponent } from './sports-hall-update.component';
import { SportsHallDeleteDialogComponent } from './sports-hall-delete-dialog.component';
import { sportsHallRoute } from './sports-hall.route';

@NgModule({
  imports: [SportComplexFinderSharedModule, RouterModule.forChild(sportsHallRoute)],
  declarations: [SportsHallComponent, SportsHallDetailComponent, SportsHallUpdateComponent, SportsHallDeleteDialogComponent],
  entryComponents: [SportsHallDeleteDialogComponent],
})
export class SportComplexFinderSportsHallModule {}
