import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { KupacSharedModule } from 'app/shared/shared.module';
import { KupacCoreModule } from 'app/core/core.module';
import { KupacAppRoutingModule } from './app-routing.module';
import { KupacHomeModule } from './home/home.module';
import { KupacEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    KupacSharedModule,
    KupacCoreModule,
    KupacHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    KupacEntityModule,
    KupacAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class KupacAppModule {}
