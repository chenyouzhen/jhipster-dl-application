import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBomEntry, BomEntry } from 'app/shared/model/bom-entry.model';
import { BomEntryService } from './bom-entry.service';
import { IMaterial } from 'app/shared/model/material.model';
import { MaterialService } from 'app/entities/material/material.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

type SelectableEntity = IMaterial | IProduct;

@Component({
  selector: 'jhi-bom-entry-update',
  templateUrl: './bom-entry-update.component.html'
})
export class BomEntryUpdateComponent implements OnInit {
  isSaving = false;
  ids: IMaterial[] = [];
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    material: [null, [Validators.required]],
    amount: [null, [Validators.required]],
    idId: [],
    productIdId: []
  });

  constructor(
    protected bomEntryService: BomEntryService,
    protected materialService: MaterialService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bomEntry }) => {
      this.updateForm(bomEntry);

      this.materialService
        .query({ filter: 'bomentryid-is-null' })
        .pipe(
          map((res: HttpResponse<IMaterial[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IMaterial[]) => {
          if (!bomEntry.idId) {
            this.ids = resBody;
          } else {
            this.materialService
              .find(bomEntry.idId)
              .pipe(
                map((subRes: HttpResponse<IMaterial>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IMaterial[]) => (this.ids = concatRes));
          }
        });

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(bomEntry: IBomEntry): void {
    this.editForm.patchValue({
      id: bomEntry.id,
      material: bomEntry.material,
      amount: bomEntry.amount,
      idId: bomEntry.idId,
      productIdId: bomEntry.productIdId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bomEntry = this.createFromForm();
    if (bomEntry.id !== undefined) {
      this.subscribeToSaveResponse(this.bomEntryService.update(bomEntry));
    } else {
      this.subscribeToSaveResponse(this.bomEntryService.create(bomEntry));
    }
  }

  private createFromForm(): IBomEntry {
    return {
      ...new BomEntry(),
      id: this.editForm.get(['id'])!.value,
      material: this.editForm.get(['material'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      idId: this.editForm.get(['idId'])!.value,
      productIdId: this.editForm.get(['productIdId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBomEntry>>): void {
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
