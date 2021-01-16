import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKorpa, Korpa } from 'app/shared/model/korpa.model';
import { KorpaService } from './korpa.service';
import { KorpaComponent } from './korpa.component';
import { KorpaDetailComponent } from './korpa-detail.component';
import { KorpaUpdateComponent } from './korpa-update.component';

@Injectable({ providedIn: 'root' })
export class KorpaResolve implements Resolve<IKorpa> {
  constructor(private service: KorpaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKorpa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((korpa: HttpResponse<Korpa>) => {
          if (korpa.body) {
            return of(korpa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Korpa());
  }
}

export const korpaRoute: Routes = [
  {
    path: '',
    component: KorpaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Korpas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KorpaDetailComponent,
    resolve: {
      korpa: KorpaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Korpas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KorpaUpdateComponent,
    resolve: {
      korpa: KorpaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Korpas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KorpaUpdateComponent,
    resolve: {
      korpa: KorpaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Korpas',
    },
    canActivate: [UserRouteAccessService],
  },
];
