import { NgModule } from '@angular/core';
import { JhipsterDlApplicationSharedLibsModule } from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';

@NgModule({
  imports: [JhipsterDlApplicationSharedLibsModule],
  declarations: [AlertComponent, AlertErrorComponent, HasAnyAuthorityDirective],
  exports: [JhipsterDlApplicationSharedLibsModule, AlertComponent, AlertErrorComponent, HasAnyAuthorityDirective]
})
export class JhipsterDlApplicationSharedModule {}
