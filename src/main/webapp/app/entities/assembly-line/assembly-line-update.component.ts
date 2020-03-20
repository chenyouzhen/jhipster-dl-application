import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAssemblyLine, AssemblyLine } from 'app/shared/model/assembly-line.model';
import { AssemblyLineService } from './assembly-line.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

@Component({
  selector: 'jhi-assembly-line-update',
  templateUrl: './assembly-line-update.component.html'
})
export class AssemblyLineUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    capacity: [null, [Validators.required]],
    planCapacity: [null, [Validators.required]],
    deviceId: [null, [Validators.required]],
    productId: [null, Validators.required]
  });

  constructor(
    protected assemblyLineService: AssemblyLineService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assemblyLine }) => {
      this.updateForm(assemblyLine);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(assemblyLine: IAssemblyLine): void {
    this.editForm.patchValue({
      id: assemblyLine.id,
      name: assemblyLine.name,
      description: assemblyLine.description,
      capacity: assemblyLine.capacity,
      planCapacity: assemblyLine.planCapacity,
      deviceId: assemblyLine.deviceId,
      productId: assemblyLine.productId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assemblyLine = this.createFromForm();
    if (assemblyLine.id !== undefined) {
      this.subscribeToSaveResponse(this.assemblyLineService.update(assemblyLine));
    } else {
      this.subscribeToSaveResponse(this.assemblyLineService.create(assemblyLine));
    }
  }

  private createFromForm(): IAssemblyLine {
    return {
      ...new AssemblyLine(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      capacity: this.editForm.get(['capacity'])!.value,
      planCapacity: this.editForm.get(['planCapacity'])!.value,
      deviceId: this.editForm.get(['deviceId'])!.value,
      productId: this.editForm.get(['productId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssemblyLine>>): void {
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
