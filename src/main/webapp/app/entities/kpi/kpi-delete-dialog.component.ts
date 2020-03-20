import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKpi } from 'app/shared/model/kpi.model';
import { KpiService } from './kpi.service';

@Component({
  templateUrl: './kpi-delete-dialog.component.html'
})
export class KpiDeleteDialogComponent {
  kpi?: IKpi;

  constructor(protected kpiService: KpiService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kpiService.delete(id).subscribe(() => {
      this.eventManager.broadcast('kpiListModification');
      this.activeModal.close();
    });
  }
}
