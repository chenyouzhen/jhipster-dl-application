import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductStock } from 'app/shared/model/product-stock.model';
import { ProductStockService } from './product-stock.service';
import { ProductStockDeleteDialogComponent } from './product-stock-delete-dialog.component';

@Component({
  selector: 'jhi-product-stock',
  templateUrl: './product-stock.component.html'
})
export class ProductStockComponent implements OnInit, OnDestroy {
  productStocks?: IProductStock[];
  eventSubscriber?: Subscription;

  constructor(
    protected productStockService: ProductStockService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productStockService.query().subscribe((res: HttpResponse<IProductStock[]>) => (this.productStocks = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductStocks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductStock): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductStocks(): void {
    this.eventSubscriber = this.eventManager.subscribe('productStockListModification', () => this.loadAll());
  }

  delete(productStock: IProductStock): void {
    const modalRef = this.modalService.open(ProductStockDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productStock = productStock;
  }
}
