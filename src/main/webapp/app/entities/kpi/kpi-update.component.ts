import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IKpi, Kpi } from 'app/shared/model/kpi.model';
import { KpiService } from './kpi.service';
import { IFactory } from 'app/shared/model/factory.model';
import { FactoryService } from 'app/entities/factory/factory.service';

@Component({
  selector: 'jhi-kpi-update',
  templateUrl: './kpi-update.component.html'
})
export class KpiUpdateComponent implements OnInit {
  isSaving = false;
  factories: IFactory[] = [];

  editForm = this.fb.group({
    id: [],
    resultTime: [null, [Validators.required]],
    type: [null, [Validators.required]],
    value: [null, [Validators.required]],
    factoryId: [null, Validators.required]
  });

  constructor(
    protected kpiService: KpiService,
    protected factoryService: FactoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kpi }) => {
      if (!kpi.id) {
        const today = moment().startOf('day');
        kpi.resultTime = today;
      }

      this.updateForm(kpi);

      this.factoryService.query().subscribe((res: HttpResponse<IFactory[]>) => (this.factories = res.body || []));
    });
  }

  updateForm(kpi: IKpi): void {
    this.editForm.patchValue({
      id: kpi.id,
      resultTime: kpi.resultTime ? kpi.resultTime.format(DATE_TIME_FORMAT) : null,
      type: kpi.type,
      value: kpi.value,
      factoryId: kpi.factoryId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kpi = this.createFromForm();
    if (kpi.id !== undefined) {
      this.subscribeToSaveResponse(this.kpiService.update(kpi));
    } else {
      this.subscribeToSaveResponse(this.kpiService.create(kpi));
    }
  }

  private createFromForm(): IKpi {
    return {
      ...new Kpi(),
      id: this.editForm.get(['id'])!.value,
      resultTime: this.editForm.get(['resultTime'])!.value ? moment(this.editForm.get(['resultTime'])!.value, DATE_TIME_FORMAT) : undefined,
      type: this.editForm.get(['type'])!.value,
      value: this.editForm.get(['value'])!.value,
      factoryId: this.editForm.get(['factoryId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKpi>>): void {
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

  trackById(index: number, item: IFactory): any {
    return item.id;
  }
}
