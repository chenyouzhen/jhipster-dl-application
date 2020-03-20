import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductStock } from 'app/shared/model/product-stock.model';

@Component({
  selector: 'jhi-product-stock-detail',
  templateUrl: './product-stock-detail.component.html'
})
export class ProductStockDetailComponent implements OnInit {
  productStock: IProductStock | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productStock }) => (this.productStock = productStock));
  }

  previousState(): void {
    window.history.back();
  }
}
