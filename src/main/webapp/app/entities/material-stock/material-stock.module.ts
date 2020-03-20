import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterDlApplicationSharedModule } from 'app/shared/shared.module';
import { MaterialStockComponent } from './material-stock.component';
import { MaterialStockDetailComponent } from './material-stock-detail.component';
import { MaterialStockUpdateComponent } from './material-stock-update.component';
import { MaterialStockDeleteDialogComponent } from './material-stock-delete-dialog.component';
import { materialStockRoute } from './material-stock.route';

@NgModule({
  imports: [JhipsterDlApplicationSharedModule, RouterModule.forChild(materialStockRoute)],
  declarations: [MaterialStockComponent, MaterialStockDetailComponent, MaterialStockUpdateComponent, MaterialStockDeleteDialogComponent],
  entryComponents: [MaterialStockDeleteDialogComponent]
})
export class JhipsterDlApplicationMaterialStockModule {}
