import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMaterialStock, MaterialStock } from 'app/shared/model/material-stock.model';
import { MaterialStockService } from './material-stock.service';

@Component({
  selector: 'jhi-material-stock-update',
  templateUrl: './material-stock-update.component.html'
})
export class MaterialStockUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    unit: []
  });

  constructor(protected materialStockService: MaterialStockService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materialStock }) => {
      this.updateForm(materialStock);
    });
  }

  updateForm(materialStock: IMaterialStock): void {
    this.editForm.patchValue({
      id: materialStock.id,
      quantity: materialStock.quantity,
      unit: materialStock.unit
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const materialStock = this.createFromForm();
    if (materialStock.id !== undefined) {
      this.subscribeToSaveResponse(this.materialStockService.update(materialStock));
    } else {
      this.subscribeToSaveResponse(this.materialStockService.create(materialStock));
    }
  }

  private createFromForm(): IMaterialStock {
    return {
      ...new MaterialStock(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      unit: this.editForm.get(['unit'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMaterialStock>>): void {
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
