import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { AssemblyLineComponent } from 'app/entities/assembly-line/assembly-line.component';
import { AssemblyLineService } from 'app/entities/assembly-line/assembly-line.service';
import { AssemblyLine } from 'app/shared/model/assembly-line.model';

describe('Component Tests', () => {
  describe('AssemblyLine Management Component', () => {
    let comp: AssemblyLineComponent;
    let fixture: ComponentFixture<AssemblyLineComponent>;
    let service: AssemblyLineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [AssemblyLineComponent]
      })
        .overrideTemplate(AssemblyLineComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssemblyLineComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssemblyLineService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AssemblyLine(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.assemblyLines && comp.assemblyLines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
