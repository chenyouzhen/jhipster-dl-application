import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { AlarmComponent } from 'app/entities/alarm/alarm.component';
import { AlarmService } from 'app/entities/alarm/alarm.service';
import { Alarm } from 'app/shared/model/alarm.model';

describe('Component Tests', () => {
  describe('Alarm Management Component', () => {
    let comp: AlarmComponent;
    let fixture: ComponentFixture<AlarmComponent>;
    let service: AlarmService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [AlarmComponent]
      })
        .overrideTemplate(AlarmComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlarmComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlarmService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Alarm(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.alarms && comp.alarms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
