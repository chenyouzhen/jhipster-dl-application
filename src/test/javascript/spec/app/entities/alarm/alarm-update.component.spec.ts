import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { AlarmUpdateComponent } from 'app/entities/alarm/alarm-update.component';
import { AlarmService } from 'app/entities/alarm/alarm.service';
import { Alarm } from 'app/shared/model/alarm.model';

describe('Component Tests', () => {
  describe('Alarm Management Update Component', () => {
    let comp: AlarmUpdateComponent;
    let fixture: ComponentFixture<AlarmUpdateComponent>;
    let service: AlarmService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [AlarmUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AlarmUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlarmUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlarmService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Alarm(123);
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
        const entity = new Alarm();
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
