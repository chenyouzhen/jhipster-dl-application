import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlarm, Alarm } from 'app/shared/model/alarm.model';
import { AlarmService } from './alarm.service';
import { AlarmComponent } from './alarm.component';
import { AlarmDetailComponent } from './alarm-detail.component';
import { AlarmUpdateComponent } from './alarm-update.component';

@Injectable({ providedIn: 'root' })
export class AlarmResolve implements Resolve<IAlarm> {
  constructor(private service: AlarmService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlarm> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alarm: HttpResponse<Alarm>) => {
          if (alarm.body) {
            return of(alarm.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Alarm());
  }
}

export const alarmRoute: Routes = [
  {
    path: '',
    component: AlarmComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Alarms'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AlarmDetailComponent,
    resolve: {
      alarm: AlarmResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Alarms'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AlarmUpdateComponent,
    resolve: {
      alarm: AlarmResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Alarms'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AlarmUpdateComponent,
    resolve: {
      alarm: AlarmResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Alarms'
    },
    canActivate: [UserRouteAccessService]
  }
];
