import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { AssemblyLineDetailComponent } from 'app/entities/assembly-line/assembly-line-detail.component';
import { AssemblyLine } from 'app/shared/model/assembly-line.model';

describe('Component Tests', () => {
  describe('AssemblyLine Management Detail Component', () => {
    let comp: AssemblyLineDetailComponent;
    let fixture: ComponentFixture<AssemblyLineDetailComponent>;
    const route = ({ data: of({ assemblyLine: new AssemblyLine(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [AssemblyLineDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AssemblyLineDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssemblyLineDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load assemblyLine on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.assemblyLine).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
