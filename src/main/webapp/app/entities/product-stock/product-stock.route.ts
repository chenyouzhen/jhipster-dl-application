import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductStock, ProductStock } from 'app/shared/model/product-stock.model';
import { ProductStockService } from './product-stock.service';
import { ProductStockComponent } from './product-stock.component';
import { ProductStockDetailComponent } from './product-stock-detail.component';
import { ProductStockUpdateComponent } from './product-stock-update.component';

@Injectable({ providedIn: 'root' })
export class ProductStockResolve implements Resolve<IProductStock> {
  constructor(private service: ProductStockService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductStock> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productStock: HttpResponse<ProductStock>) => {
          if (productStock.body) {
            return of(productStock.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductStock());
  }
}

export const productStockRoute: Routes = [
  {
    path: '',
    component: ProductStockComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ProductStocks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductStockDetailComponent,
    resolve: {
      productStock: ProductStockResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ProductStocks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductStockUpdateComponent,
    resolve: {
      productStock: ProductStockResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ProductStocks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductStockUpdateComponent,
    resolve: {
      productStock: ProductStockResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ProductStocks'
    },
    canActivate: [UserRouteAccessService]
  }
];
