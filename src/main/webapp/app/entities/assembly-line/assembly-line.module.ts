import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterDlApplicationSharedModule } from 'app/shared/shared.module';
import { AssemblyLineComponent } from './assembly-line.component';
import { AssemblyLineDetailComponent } from './assembly-line-detail.component';
import { AssemblyLineUpdateComponent } from './assembly-line-update.component';
import { AssemblyLineDeleteDialogComponent } from './assembly-line-delete-dialog.component';
import { assemblyLineRoute } from './assembly-line.route';

@NgModule({
  imports: [JhipsterDlApplicationSharedModule, RouterModule.forChild(assemblyLineRoute)],
  declarations: [AssemblyLineComponent, AssemblyLineDetailComponent, AssemblyLineUpdateComponent, AssemblyLineDeleteDialogComponent],
  entryComponents: [AssemblyLineDeleteDialogComponent]
})
export class JhipsterDlApplicationAssemblyLineModule {}
