import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterDlApplicationSharedModule } from 'app/shared/shared.module';
import { AlarmComponent } from './alarm.component';
import { AlarmDetailComponent } from './alarm-detail.component';
import { AlarmUpdateComponent } from './alarm-update.component';
import { AlarmDeleteDialogComponent } from './alarm-delete-dialog.component';
import { alarmRoute } from './alarm.route';

@NgModule({
  imports: [JhipsterDlApplicationSharedModule, RouterModule.forChild(alarmRoute)],
  declarations: [AlarmComponent, AlarmDetailComponent, AlarmUpdateComponent, AlarmDeleteDialogComponent],
  entryComponents: [AlarmDeleteDialogComponent]
})
export class JhipsterDlApplicationAlarmModule {}
