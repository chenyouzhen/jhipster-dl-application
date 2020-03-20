import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { BomEntryComponent } from 'app/entities/bom-entry/bom-entry.component';
import { BomEntryService } from 'app/entities/bom-entry/bom-entry.service';
import { BomEntry } from 'app/shared/model/bom-entry.model';

describe('Component Tests', () => {
  describe('BomEntry Management Component', () => {
    let comp: BomEntryComponent;
    let fixture: ComponentFixture<BomEntryComponent>;
    let service: BomEntryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [BomEntryComponent]
      })
        .overrideTemplate(BomEntryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BomEntryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BomEntryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BomEntry(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bomEntries && comp.bomEntries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
