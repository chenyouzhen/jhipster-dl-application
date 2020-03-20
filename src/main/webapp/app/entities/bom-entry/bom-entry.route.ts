import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBomEntry, BomEntry } from 'app/shared/model/bom-entry.model';
import { BomEntryService } from './bom-entry.service';
import { BomEntryComponent } from './bom-entry.component';
import { BomEntryDetailComponent } from './bom-entry-detail.component';
import { BomEntryUpdateComponent } from './bom-entry-update.component';

@Injectable({ providedIn: 'root' })
export class BomEntryResolve implements Resolve<IBomEntry> {
  constructor(private service: BomEntryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBomEntry> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bomEntry: HttpResponse<BomEntry>) => {
          if (bomEntry.body) {
            return of(bomEntry.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BomEntry());
  }
}

export const bomEntryRoute: Routes = [
  {
    path: '',
    component: BomEntryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BomEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BomEntryDetailComponent,
    resolve: {
      bomEntry: BomEntryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BomEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BomEntryUpdateComponent,
    resolve: {
      bomEntry: BomEntryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BomEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BomEntryUpdateComponent,
    resolve: {
      bomEntry: BomEntryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BomEntries'
    },
    canActivate: [UserRouteAccessService]
  }
];
