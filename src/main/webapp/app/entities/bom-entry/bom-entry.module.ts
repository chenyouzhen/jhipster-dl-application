import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterDlApplicationSharedModule } from 'app/shared/shared.module';
import { BomEntryComponent } from './bom-entry.component';
import { BomEntryDetailComponent } from './bom-entry-detail.component';
import { BomEntryUpdateComponent } from './bom-entry-update.component';
import { BomEntryDeleteDialogComponent } from './bom-entry-delete-dialog.component';
import { bomEntryRoute } from './bom-entry.route';

@NgModule({
  imports: [JhipsterDlApplicationSharedModule, RouterModule.forChild(bomEntryRoute)],
  declarations: [BomEntryComponent, BomEntryDetailComponent, BomEntryUpdateComponent, BomEntryDeleteDialogComponent],
  entryComponents: [BomEntryDeleteDialogComponent]
})
export class JhipsterDlApplicationBomEntryModule {}
