import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBomEntry } from 'app/shared/model/bom-entry.model';

@Component({
  selector: 'jhi-bom-entry-detail',
  templateUrl: './bom-entry-detail.component.html'
})
export class BomEntryDetailComponent implements OnInit {
  bomEntry: IBomEntry | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bomEntry }) => (this.bomEntry = bomEntry));
  }

  previousState(): void {
    window.history.back();
  }
}
