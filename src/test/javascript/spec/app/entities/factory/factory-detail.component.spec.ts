import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterDlApplicationTestModule } from '../../../test.module';
import { FactoryDetailComponent } from 'app/entities/factory/factory-detail.component';
import { Factory } from 'app/shared/model/factory.model';

describe('Component Tests', () => {
  describe('Factory Management Detail Component', () => {
    let comp: FactoryDetailComponent;
    let fixture: ComponentFixture<FactoryDetailComponent>;
    const route = ({ data: of({ factory: new Factory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterDlApplicationTestModule],
        declarations: [FactoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FactoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FactoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load factory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.factory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
