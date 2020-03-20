import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDemand, Demand } from 'app/shared/model/demand.model';
import { DemandService } from './demand.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';

type SelectableEntity = IProduct | IOrder;

@Component({
  selector: 'jhi-demand-update',
  templateUrl: './demand-update.component.html'
})
export class DemandUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];
  orders: IOrder[] = [];

  editForm = this.fb.group({
    id: [],
    demandId: [null, [Validators.required]],
    quantity: [null, [Validators.required]],
    productIdId: [],
    orderId: []
  });

  constructor(
    protected demandService: DemandService,
    protected productService: ProductService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demand }) => {
      this.updateForm(demand);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));
    });
  }

  updateForm(demand: IDemand): void {
    this.editForm.patchValue({
      id: demand.id,
      demandId: demand.demandId,
      quantity: demand.quantity,
      productIdId: demand.productIdId,
      orderId: demand.orderId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demand = this.createFromForm();
    if (demand.id !== undefined) {
      this.subscribeToSaveResponse(this.demandService.update(demand));
    } else {
      this.subscribeToSaveResponse(this.demandService.create(demand));
    }
  }

  private createFromForm(): IDemand {
    return {
      ...new Demand(),
      id: this.editForm.get(['id'])!.value,
      demandId: this.editForm.get(['demandId'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      productIdId: this.editForm.get(['productIdId'])!.value,
      orderId: this.editForm.get(['orderId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemand>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
