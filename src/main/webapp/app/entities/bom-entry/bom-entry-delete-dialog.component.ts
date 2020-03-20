import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBomEntry } from 'app/shared/model/bom-entry.model';
import { BomEntryService } from './bom-entry.service';

@Component({
  templateUrl: './bom-entry-delete-dialog.component.html'
})
export class BomEntryDeleteDialogComponent {
  bomEntry?: IBomEntry;

  constructor(protected bomEntryService: BomEntryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bomEntryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bomEntryListModification');
      this.activeModal.close();
    });
  }
}
