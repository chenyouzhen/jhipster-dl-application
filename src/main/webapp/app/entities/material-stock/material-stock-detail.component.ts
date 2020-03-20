import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMaterialStock } from 'app/shared/model/material-stock.model';

@Component({
  selector: 'jhi-material-stock-detail',
  templateUrl: './material-stock-detail.component.html'
})
export class MaterialStockDetailComponent implements OnInit {
  materialStock: IMaterialStock | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materialStock }) => (this.materialStock = materialStock));
  }

  previousState(): void {
    window.history.back();
  }
}
