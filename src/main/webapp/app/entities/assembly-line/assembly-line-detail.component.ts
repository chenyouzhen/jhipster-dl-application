import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssemblyLine } from 'app/shared/model/assembly-line.model';

@Component({
  selector: 'jhi-assembly-line-detail',
  templateUrl: './assembly-line-detail.component.html'
})
export class AssemblyLineDetailComponent implements OnInit {
  assemblyLine: IAssemblyLine | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assemblyLine }) => (this.assemblyLine = assemblyLine));
  }

  previousState(): void {
    window.history.back();
  }
}
