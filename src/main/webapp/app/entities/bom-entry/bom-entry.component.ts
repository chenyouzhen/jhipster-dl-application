import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBomEntry } from 'app/shared/model/bom-entry.model';
import { BomEntryService } from './bom-entry.service';
import { BomEntryDeleteDialogComponent } from './bom-entry-delete-dialog.component';

@Component({
  selector: 'jhi-bom-entry',
  templateUrl: './bom-entry.component.html'
})
export class BomEntryComponent implements OnInit, OnDestroy {
  bomEntries?: IBomEntry[];
  eventSubscriber?: Subscription;

  constructor(protected bomEntryService: BomEntryService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.bomEntryService.query().subscribe((res: HttpResponse<IBomEntry[]>) => (this.bomEntries = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBomEntries();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBomEntry): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBomEntries(): void {
    this.eventSubscriber = this.eventManager.subscribe('bomEntryListModification', () => this.loadAll());
  }

  delete(bomEntry: IBomEntry): void {
    const modalRef = this.modalService.open(BomEntryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bomEntry = bomEntry;
  }
}
