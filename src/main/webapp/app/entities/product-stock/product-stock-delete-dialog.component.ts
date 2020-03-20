import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductStock } from 'app/shared/model/product-stock.model';
import { ProductStockService } from './product-stock.service';

@Component({
  templateUrl: './product-stock-delete-dialog.component.html'
})
export class ProductStockDeleteDialogComponent {
  productStock?: IProductStock;

  constructor(
    protected productStockService: ProductStockService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productStockService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productStockListModification');
      this.activeModal.close();
    });
  }
}
