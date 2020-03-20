import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IMaterial, Material } from 'app/shared/model/material.model';
import { MaterialService } from './material.service';
import { IMaterialStock } from 'app/shared/model/material-stock.model';
import { MaterialStockService } from 'app/entities/material-stock/material-stock.service';

@Component({
  selector: 'jhi-material-update',
  templateUrl: './material-update.component.html'
})
export class MaterialUpdateComponent implements OnInit {
  isSaving = false;
  ids: IMaterialStock[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    unit: [null, [Validators.required]],
    description: [],
    idId: []
  });

  constructor(
    protected materialService: MaterialService,
    protected materialStockService: MaterialStockService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ material }) => {
      this.updateForm(material);

      this.materialStockService
        .query({ filter: 'materialid-is-null' })
        .pipe(
          map((res: HttpResponse<IMaterialStock[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IMaterialStock[]) => {
          if (!material.idId) {
            this.ids = resBody;
          } else {
            this.materialStockService
              .find(material.idId)
              .pipe(
                map((subRes: HttpResponse<IMaterialStock>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IMaterialStock[]) => (this.ids = concatRes));
          }
        });
    });
  }

  updateForm(material: IMaterial): void {
    this.editForm.patchValue({
      id: material.id,
      name: material.name,
      unit: material.unit,
      description: material.description,
      idId: material.idId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const material = this.createFromForm();
    if (material.id !== undefined) {
      this.subscribeToSaveResponse(this.materialService.update(material));
    } else {
      this.subscribeToSaveResponse(this.materialService.create(material));
    }
  }

  private createFromForm(): IMaterial {
    return {
      ...new Material(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      unit: this.editForm.get(['unit'])!.value,
      description: this.editForm.get(['description'])!.value,
      idId: this.editForm.get(['idId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMaterial>>): void {
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

  trackById(index: number, item: IMaterialStock): any {
    return item.id;
  }
}
