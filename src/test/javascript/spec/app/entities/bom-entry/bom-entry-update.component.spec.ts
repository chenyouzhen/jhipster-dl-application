import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { BomEntryUpdateComponent } from 'app/entities/bom-entry/bom-entry-update.component';
import { BomEntryService } from 'app/entities/bom-entry/bom-entry.service';
import { BomEntry } from 'app/shared/model/bom-entry.model';

describe('Component Tests', () => {
  describe('BomEntry Management Update Component', () => {
    let comp: BomEntryUpdateComponent;
    let fixture: ComponentFixture<BomEntryUpdateComponent>;
    let service: BomEntryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [BomEntryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BomEntryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BomEntryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BomEntryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BomEntry(123);
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
        const entity = new BomEntry();
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
