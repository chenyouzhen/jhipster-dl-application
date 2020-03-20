import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { ILogistics } from 'app/shared/model/logistics.model';
import { LogisticsService } from 'app/entities/logistics/logistics.service';
import { IFactory } from 'app/shared/model/factory.model';
import { FactoryService } from 'app/entities/factory/factory.service';

type SelectableEntity = ILogistics | IFactory;

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html'
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;
  ids: ILogistics[] = [];
  factories: IFactory[] = [];

  editForm = this.fb.group({
    id: [],
    orderId: [null, [Validators.required]],
    customer: [null, [Validators.required]],
    beginTime: [null, [Validators.required]],
    deadLine: [null, [Validators.required]],
    status: [null, [Validators.required]],
    details: [],
    idId: [],
    factoryId: []
  });

  constructor(
    protected orderService: OrderService,
    protected logisticsService: LogisticsService,
    protected factoryService: FactoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      if (!order.id) {
        const today = moment().startOf('day');
        order.beginTime = today;
        order.deadLine = today;
      }

      this.updateForm(order);

      this.logisticsService
        .query({ filter: 'orderid-is-null' })
        .pipe(
          map((res: HttpResponse<ILogistics[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ILogistics[]) => {
          if (!order.idId) {
            this.ids = resBody;
          } else {
            this.logisticsService
              .find(order.idId)
              .pipe(
                map((subRes: HttpResponse<ILogistics>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILogistics[]) => (this.ids = concatRes));
          }
        });

      this.factoryService.query().subscribe((res: HttpResponse<IFactory[]>) => (this.factories = res.body || []));
    });
  }

  updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      orderId: order.orderId,
      customer: order.customer,
      beginTime: order.beginTime ? order.beginTime.format(DATE_TIME_FORMAT) : null,
      deadLine: order.deadLine ? order.deadLine.format(DATE_TIME_FORMAT) : null,
      status: order.status,
      details: order.details,
      idId: order.idId,
      factoryId: order.factoryId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id'])!.value,
      orderId: this.editForm.get(['orderId'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      beginTime: this.editForm.get(['beginTime'])!.value ? moment(this.editForm.get(['beginTime'])!.value, DATE_TIME_FORMAT) : undefined,
      deadLine: this.editForm.get(['deadLine'])!.value ? moment(this.editForm.get(['deadLine'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
      details: this.editForm.get(['details'])!.value,
      idId: this.editForm.get(['idId'])!.value,
      factoryId: this.editForm.get(['factoryId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
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
