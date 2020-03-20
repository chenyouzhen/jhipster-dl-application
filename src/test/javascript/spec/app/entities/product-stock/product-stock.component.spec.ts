import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { ProductStockComponent } from 'app/entities/product-stock/product-stock.component';
import { ProductStockService } from 'app/entities/product-stock/product-stock.service';
import { ProductStock } from 'app/shared/model/product-stock.model';

describe('Component Tests', () => {
  describe('ProductStock Management Component', () => {
    let comp: ProductStockComponent;
    let fixture: ComponentFixture<ProductStockComponent>;
    let service: ProductStockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [ProductStockComponent]
      })
        .overrideTemplate(ProductStockComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductStockComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductStockService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductStock(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productStocks && comp.productStocks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
