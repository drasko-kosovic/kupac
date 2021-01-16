import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KupacSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [KupacSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class KupacHomeModule {}
