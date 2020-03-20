import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { MaterialStockComponent } from 'app/entities/material-stock/material-stock.component';
import { MaterialStockService } from 'app/entities/material-stock/material-stock.service';
import { MaterialStock } from 'app/shared/model/material-stock.model';

describe('Component Tests', () => {
  describe('MaterialStock Management Component', () => {
    let comp: MaterialStockComponent;
    let fixture: ComponentFixture<MaterialStockComponent>;
    let service: MaterialStockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [MaterialStockComponent]
      })
        .overrideTemplate(MaterialStockComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MaterialStockComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MaterialStockService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MaterialStock(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.materialStocks && comp.materialStocks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
