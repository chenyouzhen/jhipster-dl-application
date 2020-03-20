import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { LogisticsDetailComponent } from 'app/entities/logistics/logistics-detail.component';
import { Logistics } from 'app/shared/model/logistics.model';

describe('Component Tests', () => {
  describe('Logistics Management Detail Component', () => {
    let comp: LogisticsDetailComponent;
    let fixture: ComponentFixture<LogisticsDetailComponent>;
    const route = ({ data: of({ logistics: new Logistics(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [LogisticsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LogisticsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LogisticsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load logistics on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.logistics).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
