import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { BomEntryDetailComponent } from 'app/entities/bom-entry/bom-entry-detail.component';
import { BomEntry } from 'app/shared/model/bom-entry.model';

describe('Component Tests', () => {
  describe('BomEntry Management Detail Component', () => {
    let comp: BomEntryDetailComponent;
    let fixture: ComponentFixture<BomEntryDetailComponent>;
    const route = ({ data: of({ bomEntry: new BomEntry(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [BomEntryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BomEntryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BomEntryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bomEntry on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bomEntry).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
