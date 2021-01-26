import { Routes } from '@angular/router';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

import { AngularComponent } from './angular.component';

export const angularRoute: Routes = [
  {
    path: '',
    component: AngularComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Angulars',
    },
    canActivate: [UserRouteAccessService],
  },
];
