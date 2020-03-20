import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFactory } from 'app/shared/model/factory.model';

@Component({
  selector: 'jhi-factory-detail',
  templateUrl: './factory-detail.component.html'
})
export class FactoryDetailComponent implements OnInit {
  factory: IFactory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factory }) => (this.factory = factory));
  }

  previousState(): void {
    window.history.back();
  }
}
