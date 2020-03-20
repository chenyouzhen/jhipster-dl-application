import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { IProductStock } from 'app/shared/model/product-stock.model';
import { ProductStockService } from 'app/entities/product-stock/product-stock.service';
import { IFactory } from 'app/shared/model/factory.model';
import { FactoryService } from 'app/entities/factory/factory.service';

type SelectableEntity = IProductStock | IFactory;

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  ids: IProductStock[] = [];
  factories: IFactory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    category: [null, [Validators.required]],
    model: [null, [Validators.required]],
    description: [null, [Validators.required]],
    idId: [],
    factoryId: []
  });

  constructor(
    protected productService: ProductService,
    protected productStockService: ProductStockService,
    protected factoryService: FactoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);

      this.productStockService
        .query({ filter: 'productid-is-null' })
        .pipe(
          map((res: HttpResponse<IProductStock[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IProductStock[]) => {
          if (!product.idId) {
            this.ids = resBody;
          } else {
            this.productStockService
              .find(product.idId)
              .pipe(
                map((subRes: HttpResponse<IProductStock>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IProductStock[]) => (this.ids = concatRes));
          }
        });

      this.factoryService.query().subscribe((res: HttpResponse<IFactory[]>) => (this.factories = res.body || []));
    });
  }

  updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      name: product.name,
      category: product.category,
      model: product.model,
      description: product.description,
      idId: product.idId,
      factoryId: product.factoryId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      category: this.editForm.get(['category'])!.value,
      model: this.editForm.get(['model'])!.value,
      description: this.editForm.get(['description'])!.value,
      idId: this.editForm.get(['idId'])!.value,
      factoryId: this.editForm.get(['factoryId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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
