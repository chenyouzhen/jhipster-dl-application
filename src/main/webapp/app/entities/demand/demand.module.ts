import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterDlApplicationSharedModule } from 'app/shared/shared.module';
import { DemandComponent } from './demand.component';
import { DemandDetailComponent } from './demand-detail.component';
import { DemandUpdateComponent } from './demand-update.component';
import { DemandDeleteDialogComponent } from './demand-delete-dialog.component';
import { demandRoute } from './demand.route';

@NgModule({
  imports: [JhipsterDlApplicationSharedModule, RouterModule.forChild(demandRoute)],
  declarations: [DemandComponent, DemandDetailComponent, DemandUpdateComponent, DemandDeleteDialogComponent],
  entryComponents: [DemandDeleteDialogComponent]
})
export class JhipsterDlApplicationDemandModule {}
