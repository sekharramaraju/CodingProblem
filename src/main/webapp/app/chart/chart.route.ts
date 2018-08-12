import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Sku } from 'app/shared/model/sku.model';
import { TreeChartComponent } from './tree.component';

export const chartRoute: Routes = [
    {
        path: 'chart',
        component: TreeChartComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Infograhic'
        },
        canActivate: [UserRouteAccessService]
    }
];
