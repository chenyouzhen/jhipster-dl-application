import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAssemblyLine, AssemblyLine } from 'app/shared/model/assembly-line.model';
import { AssemblyLineService } from './assembly-line.service';
import { AssemblyLineComponent } from './assembly-line.component';
import { AssemblyLineDetailComponent } from './assembly-line-detail.component';
import { AssemblyLineUpdateComponent } from './assembly-line-update.component';

@Injectable({ providedIn: 'root' })
export class AssemblyLineResolve implements Resolve<IAssemblyLine> {
  constructor(private service: AssemblyLineService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssemblyLine> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((assemblyLine: HttpResponse<AssemblyLine>) => {
          if (assemblyLine.body) {
            return of(assemblyLine.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AssemblyLine());
  }
}

export const assemblyLineRoute: Routes = [
  {
    path: '',
    component: AssemblyLineComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AssemblyLines'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AssemblyLineDetailComponent,
    resolve: {
      assemblyLine: AssemblyLineResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AssemblyLines'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AssemblyLineUpdateComponent,
    resolve: {
      assemblyLine: AssemblyLineResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AssemblyLines'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AssemblyLineUpdateComponent,
    resolve: {
      assemblyLine: AssemblyLineResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AssemblyLines'
    },
    canActivate: [UserRouteAccessService]
  }
];
