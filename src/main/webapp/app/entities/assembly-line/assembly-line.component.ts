import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAssemblyLine } from 'app/shared/model/assembly-line.model';
import { AssemblyLineService } from './assembly-line.service';
import { AssemblyLineDeleteDialogComponent } from './assembly-line-delete-dialog.component';

@Component({
  selector: 'jhi-assembly-line',
  templateUrl: './assembly-line.component.html'
})
export class AssemblyLineComponent implements OnInit, OnDestroy {
  assemblyLines?: IAssemblyLine[];
  eventSubscriber?: Subscription;

  constructor(
    protected assemblyLineService: AssemblyLineService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.assemblyLineService.query().subscribe((res: HttpResponse<IAssemblyLine[]>) => (this.assemblyLines = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAssemblyLines();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAssemblyLine): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAssemblyLines(): void {
    this.eventSubscriber = this.eventManager.subscribe('assemblyLineListModification', () => this.loadAll());
  }

  delete(assemblyLine: IAssemblyLine): void {
    const modalRef = this.modalService.open(AssemblyLineDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.assemblyLine = assemblyLine;
  }
}
