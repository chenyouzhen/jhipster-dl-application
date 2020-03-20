import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterDlApplicationSharedModule } from 'app/shared/shared.module';
import { ProductStockComponent } from './product-stock.component';
import { ProductStockDetailComponent } from './product-stock-detail.component';
import { ProductStockUpdateComponent } from './product-stock-update.component';
import { ProductStockDeleteDialogComponent } from './product-stock-delete-dialog.component';
import { productStockRoute } from './product-stock.route';

@NgModule({
  imports: [JhipsterDlApplicationSharedModule, RouterModule.forChild(productStockRoute)],
  declarations: [ProductStockComponent, ProductStockDetailComponent, ProductStockUpdateComponent, ProductStockDeleteDialogComponent],
  entryComponents: [ProductStockDeleteDialogComponent]
})
export class JhipsterDlApplicationProductStockModule {}
