import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SportComplexFinderSharedModule } from 'app/shared/shared.module';

import { DocsComponent } from './docs.component';

import { docsRoute } from './docs.route';

@NgModule({
  imports: [SportComplexFinderSharedModule, RouterModule.forChild([docsRoute])],
  declarations: [DocsComponent],
})
export class DocsModule {}
