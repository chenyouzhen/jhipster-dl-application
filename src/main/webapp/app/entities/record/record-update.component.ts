import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRecord, Record } from 'app/shared/model/record.model';
import { RecordService } from './record.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

@Component({
  selector: 'jhi-record-update',
  templateUrl: './record-update.component.html'
})
export class RecordUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    dailyOutput: [],
    resultTime: [null, [Validators.required]],
    productId: [null, Validators.required]
  });

  constructor(
    protected recordService: RecordService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ record }) => {
      if (!record.id) {
        const today = moment().startOf('day');
        record.resultTime = today;
      }

      this.updateForm(record);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(record: IRecord): void {
    this.editForm.patchValue({
      id: record.id,
      dailyOutput: record.dailyOutput,
      resultTime: record.resultTime ? record.resultTime.format(DATE_TIME_FORMAT) : null,
      productId: record.productId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const record = this.createFromForm();
    if (record.id !== undefined) {
      this.subscribeToSaveResponse(this.recordService.update(record));
    } else {
      this.subscribeToSaveResponse(this.recordService.create(record));
    }
  }

  private createFromForm(): IRecord {
    return {
      ...new Record(),
      id: this.editForm.get(['id'])!.value,
      dailyOutput: this.editForm.get(['dailyOutput'])!.value,
      resultTime: this.editForm.get(['resultTime'])!.value ? moment(this.editForm.get(['resultTime'])!.value, DATE_TIME_FORMAT) : undefined,
      productId: this.editForm.get(['productId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecord>>): void {
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

  trackById(index: number, item: IProduct): any {
    return item.id;
  }
}
