import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAssemblyLine } from 'app/shared/model/assembly-line.model';
import { AssemblyLineService } from './assembly-line.service';

@Component({
  templateUrl: './assembly-line-delete-dialog.component.html'
})
export class AssemblyLineDeleteDialogComponent {
  assemblyLine?: IAssemblyLine;

  constructor(
    protected assemblyLineService: AssemblyLineService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.assemblyLineService.delete(id).subscribe(() => {
      this.eventManager.broadcast('assemblyLineListModification');
      this.activeModal.close();
    });
  }
}
