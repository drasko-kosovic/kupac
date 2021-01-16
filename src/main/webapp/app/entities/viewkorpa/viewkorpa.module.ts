import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KupacSharedModule } from 'app/shared/shared.module';
import { ViewkorpaComponent } from './viewkorpa.component';
import { ViewkorpaDetailComponent } from './viewkorpa-detail.component';
import { viewkorpaRoute } from './viewkorpa.route';

@NgModule({
  imports: [KupacSharedModule, RouterModule.forChild(viewkorpaRoute)],
  declarations: [ViewkorpaComponent, ViewkorpaDetailComponent],
})
export class KupacViewkorpaModule {}
