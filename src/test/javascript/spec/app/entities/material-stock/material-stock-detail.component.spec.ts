import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { MaterialStockDetailComponent } from 'app/entities/material-stock/material-stock-detail.component';
import { MaterialStock } from 'app/shared/model/material-stock.model';

describe('Component Tests', () => {
  describe('MaterialStock Management Detail Component', () => {
    let comp: MaterialStockDetailComponent;
    let fixture: ComponentFixture<MaterialStockDetailComponent>;
    const route = ({ data: of({ materialStock: new MaterialStock(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [MaterialStockDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MaterialStockDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MaterialStockDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load materialStock on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.materialStock).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
