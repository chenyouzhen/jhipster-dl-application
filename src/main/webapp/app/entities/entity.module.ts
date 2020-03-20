import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'factory',
        loadChildren: () => import('./factory/factory.module').then(m => m.JhipsterDlApplicationFactoryModule)
      },
      {
        path: 'assembly-line',
        loadChildren: () => import('./assembly-line/assembly-line.module').then(m => m.JhipsterDlApplicationAssemblyLineModule)
      },
      {
        path: 'observation',
        loadChildren: () => import('./observation/observation.module').then(m => m.JhipsterDlApplicationObservationModule)
      },
      {
        path: 'alarm',
        loadChildren: () => import('./alarm/alarm.module').then(m => m.JhipsterDlApplicationAlarmModule)
      },
      {
        path: 'kpi',
        loadChildren: () => import('./kpi/kpi.module').then(m => m.JhipsterDlApplicationKpiModule)
      },
      {
        path: 'record',
        loadChildren: () => import('./record/record.module').then(m => m.JhipsterDlApplicationRecordModule)
      },
      {
        path: 'logistics',
        loadChildren: () => import('./logistics/logistics.module').then(m => m.JhipsterDlApplicationLogisticsModule)
      },
      {
        path: 'demand',
        loadChildren: () => import('./demand/demand.module').then(m => m.JhipsterDlApplicationDemandModule)
      },
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.JhipsterDlApplicationOrderModule)
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.JhipsterDlApplicationProductModule)
      },
      {
        path: 'bom-entry',
        loadChildren: () => import('./bom-entry/bom-entry.module').then(m => m.JhipsterDlApplicationBomEntryModule)
      },
      {
        path: 'material',
        loadChildren: () => import('./material/material.module').then(m => m.JhipsterDlApplicationMaterialModule)
      },
      {
        path: 'product-stock',
        loadChildren: () => import('./product-stock/product-stock.module').then(m => m.JhipsterDlApplicationProductStockModule)
      },
      {
        path: 'material-stock',
        loadChildren: () => import('./material-stock/material-stock.module').then(m => m.JhipsterDlApplicationMaterialStockModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhipsterDlApplicationEntityModule {}
