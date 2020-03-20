import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMaterialStock } from 'app/shared/model/material-stock.model';
import { MaterialStockService } from './material-stock.service';

@Component({
  templateUrl: './material-stock-delete-dialog.component.html'
})
export class MaterialStockDeleteDialogComponent {
  materialStock?: IMaterialStock;

  constructor(
    protected materialStockService: MaterialStockService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.materialStockService.delete(id).subscribe(() => {
      this.eventManager.broadcast('materialStockListModification');
      this.activeModal.close();
    });
  }
}
