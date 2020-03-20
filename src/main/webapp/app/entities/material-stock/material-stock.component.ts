import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMaterialStock } from 'app/shared/model/material-stock.model';
import { MaterialStockService } from './material-stock.service';
import { MaterialStockDeleteDialogComponent } from './material-stock-delete-dialog.component';

@Component({
  selector: 'jhi-material-stock',
  templateUrl: './material-stock.component.html'
})
export class MaterialStockComponent implements OnInit, OnDestroy {
  materialStocks?: IMaterialStock[];
  eventSubscriber?: Subscription;

  constructor(
    protected materialStockService: MaterialStockService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.materialStockService.query().subscribe((res: HttpResponse<IMaterialStock[]>) => (this.materialStocks = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMaterialStocks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMaterialStock): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMaterialStocks(): void {
    this.eventSubscriber = this.eventManager.subscribe('materialStockListModification', () => this.loadAll());
  }

  delete(materialStock: IMaterialStock): void {
    const modalRef = this.modalService.open(MaterialStockDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.materialStock = materialStock;
  }
}
