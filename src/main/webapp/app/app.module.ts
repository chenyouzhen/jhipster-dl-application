import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { JhipsterDlApplicationSharedModule } from 'app/shared/shared.module';
import { JhipsterDlApplicationCoreModule } from 'app/core/core.module';
import { JhipsterDlApplicationAppRoutingModule } from './app-routing.module';
import { JhipsterDlApplicationHomeModule } from './home/home.module';
import { JhipsterDlApplicationEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    JhipsterDlApplicationSharedModule,
    JhipsterDlApplicationCoreModule,
    JhipsterDlApplicationHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JhipsterDlApplicationEntityModule,
    JhipsterDlApplicationAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class JhipsterDlApplicationAppModule {}
