import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Sku } from 'app/shared/model/sku.model';
import { SkuService } from './sku.service';
import { SkuComponent } from './sku.component';
import { SkuDetailComponent } from './sku-detail.component';
import { SkuUpdateComponent } from './sku-update.component';
import { SkuDeletePopupComponent } from './sku-delete-dialog.component';
import { ISku } from 'app/shared/model/sku.model';

@Injectable({ providedIn: 'root' })
export class SkuResolve implements Resolve<ISku> {
    constructor(private service: SkuService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sku: HttpResponse<Sku>) => sku.body));
        }
        return of(new Sku());
    }
}

export const skuRoute: Routes = [
    {
        path: 'sku',
        component: SkuComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Skus'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sku/:id/view',
        component: SkuDetailComponent,
        resolve: {
            sku: SkuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Skus'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sku/new',
        component: SkuUpdateComponent,
        resolve: {
            sku: SkuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Skus'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sku/:id/edit',
        component: SkuUpdateComponent,
        resolve: {
            sku: SkuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Skus'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const skuPopupRoute: Routes = [
    {
        path: 'sku/:id/delete',
        component: SkuDeletePopupComponent,
        resolve: {
            sku: SkuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Skus'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
