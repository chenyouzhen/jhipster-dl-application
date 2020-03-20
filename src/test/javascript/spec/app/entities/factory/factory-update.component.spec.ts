import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { FactoryUpdateComponent } from 'app/entities/factory/factory-update.component';
import { FactoryService } from 'app/entities/factory/factory.service';
import { Factory } from 'app/shared/model/factory.model';

describe('Component Tests', () => {
  describe('Factory Management Update Component', () => {
    let comp: FactoryUpdateComponent;
    let fixture: ComponentFixture<FactoryUpdateComponent>;
    let service: FactoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [FactoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FactoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FactoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FactoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Factory(123);
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
        const entity = new Factory();
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
