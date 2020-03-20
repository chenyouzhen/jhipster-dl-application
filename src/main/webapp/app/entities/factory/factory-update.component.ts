import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFactory, Factory } from 'app/shared/model/factory.model';
import { FactoryService } from './factory.service';

@Component({
  selector: 'jhi-factory-update',
  templateUrl: './factory-update.component.html'
})
export class FactoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    scale: [],
    location: [null, [Validators.required]]
  });

  constructor(protected factoryService: FactoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factory }) => {
      this.updateForm(factory);
    });
  }

  updateForm(factory: IFactory): void {
    this.editForm.patchValue({
      id: factory.id,
      name: factory.name,
      description: factory.description,
      scale: factory.scale,
      location: factory.location
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const factory = this.createFromForm();
    if (factory.id !== undefined) {
      this.subscribeToSaveResponse(this.factoryService.update(factory));
    } else {
      this.subscribeToSaveResponse(this.factoryService.create(factory));
    }
  }

  private createFromForm(): IFactory {
    return {
      ...new Factory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      scale: this.editForm.get(['scale'])!.value,
      location: this.editForm.get(['location'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFactory>>): void {
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
