import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'korpa',
        loadChildren: () => import('./korpa/korpa.module').then(m => m.KupacKorpaModule),
      },
      {
        path: 'viewkorpa',
        loadChildren: () => import('./viewkorpa/viewkorpa.module').then(m => m.KupacViewkorpaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class KupacEntityModule {}
