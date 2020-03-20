import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlarm } from 'app/shared/model/alarm.model';
import { AlarmService } from './alarm.service';

@Component({
  templateUrl: './alarm-delete-dialog.component.html'
})
export class AlarmDeleteDialogComponent {
  alarm?: IAlarm;

  constructor(protected alarmService: AlarmService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alarmService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alarmListModification');
      this.activeModal.close();
    });
  }
}
