import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KupacSharedModule } from 'app/shared/shared.module';
import { AngularComponent } from './angular.component';

import { angularRoute } from './angular.route';

@NgModule({
  imports: [KupacSharedModule, RouterModule.forChild(angularRoute)],
  declarations: [AngularComponent],
})
export class KupacAngularModule {}
