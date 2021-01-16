import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IViewkorpa, Viewkorpa } from 'app/shared/model/viewkorpa.model';
import { ViewkorpaService } from './viewkorpa.service';
import { ViewkorpaComponent } from './viewkorpa.component';
import { ViewkorpaDetailComponent } from './viewkorpa-detail.component';

@Injectable({ providedIn: 'root' })
export class ViewkorpaResolve implements Resolve<IViewkorpa> {
  constructor(private service: ViewkorpaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IViewkorpa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((viewkorpa: HttpResponse<Viewkorpa>) => {
          if (viewkorpa.body) {
            return of(viewkorpa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Viewkorpa());
  }
}

export const viewkorpaRoute: Routes = [
  {
    path: '',
    component: ViewkorpaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Viewkorpas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ViewkorpaDetailComponent,
    resolve: {
      viewkorpa: ViewkorpaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Viewkorpas',
    },
    canActivate: [UserRouteAccessService],
  },
];
