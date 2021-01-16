import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KupacSharedModule } from 'app/shared/shared.module';
import { KorpaComponent } from './korpa.component';
import { KorpaDetailComponent } from './korpa-detail.component';
import { KorpaUpdateComponent } from './korpa-update.component';
import { KorpaDeleteDialogComponent } from './korpa-delete-dialog.component';
import { korpaRoute } from './korpa.route';

@NgModule({
  imports: [KupacSharedModule, RouterModule.forChild(korpaRoute)],
  declarations: [KorpaComponent, KorpaDetailComponent, KorpaUpdateComponent, KorpaDeleteDialogComponent],
  entryComponents: [KorpaDeleteDialogComponent],
})
export class KupacKorpaModule {}
