import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterDlApplicationSharedModule } from 'app/shared/shared.module';
import { KpiComponent } from './kpi.component';
import { KpiDetailComponent } from './kpi-detail.component';
import { KpiUpdateComponent } from './kpi-update.component';
import { KpiDeleteDialogComponent } from './kpi-delete-dialog.component';
import { kpiRoute } from './kpi.route';

@NgModule({
  imports: [JhipsterDlApplicationSharedModule, RouterModule.forChild(kpiRoute)],
  declarations: [KpiComponent, KpiDetailComponent, KpiUpdateComponent, KpiDeleteDialogComponent],
  entryComponents: [KpiDeleteDialogComponent]
})
export class JhipsterDlApplicationKpiModule {}
