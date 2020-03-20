import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKpi } from 'app/shared/model/kpi.model';

@Component({
  selector: 'jhi-kpi-detail',
  templateUrl: './kpi-detail.component.html'
})
export class KpiDetailComponent implements OnInit {
  kpi: IKpi | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kpi }) => (this.kpi = kpi));
  }

  previousState(): void {
    window.history.back();
  }
}
