import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { School } from 'app/shared/model/school.model';
import { SchoolService } from './school.service';
import { SchoolComponent } from './school.component';
import { SchoolDetailComponent } from './school-detail.component';
import { SchoolUpdateComponent } from './school-update.component';
import { SchoolDeletePopupComponent } from './school-delete-dialog.component';
import { ISchool } from 'app/shared/model/school.model';

@Injectable({ providedIn: 'root' })
export class SchoolResolve implements Resolve<ISchool> {
    constructor(private service: SchoolService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((school: HttpResponse<School>) => school.body));
        }
        return of(new School());
    }
}

export const schoolRoute: Routes = [
    {
        path: 'school',
        component: SchoolComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayTestApp.school.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'school/:id/view',
        component: SchoolDetailComponent,
        resolve: {
            school: SchoolResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayTestApp.school.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'school/new',
        component: SchoolUpdateComponent,
        resolve: {
            school: SchoolResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayTestApp.school.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'school/:id/edit',
        component: SchoolUpdateComponent,
        resolve: {
            school: SchoolResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayTestApp.school.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const schoolPopupRoute: Routes = [
    {
        path: 'school/:id/delete',
        component: SchoolDeletePopupComponent,
        resolve: {
            school: SchoolResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayTestApp.school.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
