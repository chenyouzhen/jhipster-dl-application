import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMaterialStock, MaterialStock } from 'app/shared/model/material-stock.model';
import { MaterialStockService } from './material-stock.service';
import { MaterialStockComponent } from './material-stock.component';
import { MaterialStockDetailComponent } from './material-stock-detail.component';
import { MaterialStockUpdateComponent } from './material-stock-update.component';

@Injectable({ providedIn: 'root' })
export class MaterialStockResolve implements Resolve<IMaterialStock> {
  constructor(private service: MaterialStockService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMaterialStock> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((materialStock: HttpResponse<MaterialStock>) => {
          if (materialStock.body) {
            return of(materialStock.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MaterialStock());
  }
}

export const materialStockRoute: Routes = [
  {
    path: '',
    component: MaterialStockComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MaterialStocks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MaterialStockDetailComponent,
    resolve: {
      materialStock: MaterialStockResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MaterialStocks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MaterialStockUpdateComponent,
    resolve: {
      materialStock: MaterialStockResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MaterialStocks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MaterialStockUpdateComponent,
    resolve: {
      materialStock: MaterialStockResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MaterialStocks'
    },
    canActivate: [UserRouteAccessService]
  }
];
