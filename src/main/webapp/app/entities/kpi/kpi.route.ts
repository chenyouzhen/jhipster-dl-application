import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKpi, Kpi } from 'app/shared/model/kpi.model';
import { KpiService } from './kpi.service';
import { KpiComponent } from './kpi.component';
import { KpiDetailComponent } from './kpi-detail.component';
import { KpiUpdateComponent } from './kpi-update.component';

@Injectable({ providedIn: 'root' })
export class KpiResolve implements Resolve<IKpi> {
  constructor(private service: KpiService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKpi> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((kpi: HttpResponse<Kpi>) => {
          if (kpi.body) {
            return of(kpi.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Kpi());
  }
}

export const kpiRoute: Routes = [
  {
    path: '',
    component: KpiComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Kpis'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: KpiDetailComponent,
    resolve: {
      kpi: KpiResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Kpis'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: KpiUpdateComponent,
    resolve: {
      kpi: KpiResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Kpis'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: KpiUpdateComponent,
    resolve: {
      kpi: KpiResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Kpis'
    },
    canActivate: [UserRouteAccessService]
  }
];
