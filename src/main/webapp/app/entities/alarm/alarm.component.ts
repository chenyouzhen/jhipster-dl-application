import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAlarm } from 'app/shared/model/alarm.model';
import { AlarmService } from './alarm.service';
import { AlarmDeleteDialogComponent } from './alarm-delete-dialog.component';

@Component({
  selector: 'jhi-alarm',
  templateUrl: './alarm.component.html'
})
export class AlarmComponent implements OnInit, OnDestroy {
  alarms?: IAlarm[];
  eventSubscriber?: Subscription;

  constructor(protected alarmService: AlarmService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.alarmService.query().subscribe((res: HttpResponse<IAlarm[]>) => (this.alarms = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAlarms();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAlarm): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAlarms(): void {
    this.eventSubscriber = this.eventManager.subscribe('alarmListModification', () => this.loadAll());
  }

  delete(alarm: IAlarm): void {
    const modalRef = this.modalService.open(AlarmDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.alarm = alarm;
  }
}
