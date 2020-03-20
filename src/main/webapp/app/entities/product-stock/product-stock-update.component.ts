import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductStock, ProductStock } from 'app/shared/model/product-stock.model';
import { ProductStockService } from './product-stock.service';

@Component({
  selector: 'jhi-product-stock-update',
  templateUrl: './product-stock-update.component.html'
})
export class ProductStockUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    unit: []
  });

  constructor(protected productStockService: ProductStockService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productStock }) => {
      this.updateForm(productStock);
    });
  }

  updateForm(productStock: IProductStock): void {
    this.editForm.patchValue({
      id: productStock.id,
      quantity: productStock.quantity,
      unit: productStock.unit
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productStock = this.createFromForm();
    if (productStock.id !== undefined) {
      this.subscribeToSaveResponse(this.productStockService.update(productStock));
    } else {
      this.subscribeToSaveResponse(this.productStockService.create(productStock));
    }
  }

  private createFromForm(): IProductStock {
    return {
      ...new ProductStock(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      unit: this.editForm.get(['unit'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductStock>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
