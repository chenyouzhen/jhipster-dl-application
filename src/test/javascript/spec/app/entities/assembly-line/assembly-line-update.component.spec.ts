import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { AssemblyLineUpdateComponent } from 'app/entities/assembly-line/assembly-line-update.component';
import { AssemblyLineService } from 'app/entities/assembly-line/assembly-line.service';
import { AssemblyLine } from 'app/shared/model/assembly-line.model';

describe('Component Tests', () => {
  describe('AssemblyLine Management Update Component', () => {
    let comp: AssemblyLineUpdateComponent;
    let fixture: ComponentFixture<AssemblyLineUpdateComponent>;
    let service: AssemblyLineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [AssemblyLineUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AssemblyLineUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssemblyLineUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssemblyLineService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AssemblyLine(123);
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
        const entity = new AssemblyLine();
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
