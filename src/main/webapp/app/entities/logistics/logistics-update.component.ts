import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILogistics, Logistics } from 'app/shared/model/logistics.model';
import { LogisticsService } from './logistics.service';

@Component({
  selector: 'jhi-logistics-update',
  templateUrl: './logistics-update.component.html'
})
export class LogisticsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    logisticsId: [null, [Validators.required]],
    expressCompany: [],
    expressNumber: [],
    status: [null, [Validators.required]],
    startPosition: [],
    endPosition: [],
    currentPosition: []
  });

  constructor(protected logisticsService: LogisticsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logistics }) => {
      this.updateForm(logistics);
    });
  }

  updateForm(logistics: ILogistics): void {
    this.editForm.patchValue({
      id: logistics.id,
      logisticsId: logistics.logisticsId,
      expressCompany: logistics.expressCompany,
      expressNumber: logistics.expressNumber,
      status: logistics.status,
      startPosition: logistics.startPosition,
      endPosition: logistics.endPosition,
      currentPosition: logistics.currentPosition
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const logistics = this.createFromForm();
    if (logistics.id !== undefined) {
      this.subscribeToSaveResponse(this.logisticsService.update(logistics));
    } else {
      this.subscribeToSaveResponse(this.logisticsService.create(logistics));
    }
  }

  private createFromForm(): ILogistics {
    return {
      ...new Logistics(),
      id: this.editForm.get(['id'])!.value,
      logisticsId: this.editForm.get(['logisticsId'])!.value,
      expressCompany: this.editForm.get(['expressCompany'])!.value,
      expressNumber: this.editForm.get(['expressNumber'])!.value,
      status: this.editForm.get(['status'])!.value,
      startPosition: this.editForm.get(['startPosition'])!.value,
      endPosition: this.editForm.get(['endPosition'])!.value,
      currentPosition: this.editForm.get(['currentPosition'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILogistics>>): void {
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
