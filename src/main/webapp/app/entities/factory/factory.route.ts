import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFactory, Factory } from 'app/shared/model/factory.model';
import { FactoryService } from './factory.service';
import { FactoryComponent } from './factory.component';
import { FactoryDetailComponent } from './factory-detail.component';
import { FactoryUpdateComponent } from './factory-update.component';

@Injectable({ providedIn: 'root' })
export class FactoryResolve implements Resolve<IFactory> {
  constructor(private service: FactoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFactory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((factory: HttpResponse<Factory>) => {
          if (factory.body) {
            return of(factory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Factory());
  }
}

export const factoryRoute: Routes = [
  {
    path: '',
    component: FactoryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Factories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FactoryDetailComponent,
    resolve: {
      factory: FactoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Factories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FactoryUpdateComponent,
    resolve: {
      factory: FactoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Factories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FactoryUpdateComponent,
    resolve: {
      factory: FactoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Factories'
    },
    canActivate: [UserRouteAccessService]
  }
];
