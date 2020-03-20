import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IKpi } from 'app/shared/model/kpi.model';
import { KpiService } from './kpi.service';
import { KpiDeleteDialogComponent } from './kpi-delete-dialog.component';

@Component({
  selector: 'jhi-kpi',
  templateUrl: './kpi.component.html'
})
export class KpiComponent implements OnInit, OnDestroy {
  kpis?: IKpi[];
  eventSubscriber?: Subscription;

  constructor(protected kpiService: KpiService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.kpiService.query().subscribe((res: HttpResponse<IKpi[]>) => (this.kpis = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInKpis();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IKpi): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInKpis(): void {
    this.eventSubscriber = this.eventManager.subscribe('kpiListModification', () => this.loadAll());
  }

  delete(kpi: IKpi): void {
    const modalRef = this.modalService.open(KpiDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.kpi = kpi;
  }
}
