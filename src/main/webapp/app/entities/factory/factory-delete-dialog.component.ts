import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFactory } from 'app/shared/model/factory.model';
import { FactoryService } from './factory.service';

@Component({
  templateUrl: './factory-delete-dialog.component.html'
})
export class FactoryDeleteDialogComponent {
  factory?: IFactory;

  constructor(protected factoryService: FactoryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.factoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('factoryListModification');
      this.activeModal.close();
    });
  }
}
