import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { MaterialStockUpdateComponent } from 'app/entities/material-stock/material-stock-update.component';
import { MaterialStockService } from 'app/entities/material-stock/material-stock.service';
import { MaterialStock } from 'app/shared/model/material-stock.model';

describe('Component Tests', () => {
  describe('MaterialStock Management Update Component', () => {
    let comp: MaterialStockUpdateComponent;
    let fixture: ComponentFixture<MaterialStockUpdateComponent>;
    let service: MaterialStockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [MaterialStockUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MaterialStockUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MaterialStockUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MaterialStockService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MaterialStock(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MaterialStock();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
