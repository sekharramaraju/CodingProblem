import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InmarSharedModule } from 'app/shared';
import {
    SkuComponent,
    SkuDetailComponent,
    SkuUpdateComponent,
    SkuDeletePopupComponent,
    SkuDeleteDialogComponent,
    SkuSearchComponent,
    skuRoute,
    skuPopupRoute
} from './';

const ENTITY_STATES = [...skuRoute, ...skuPopupRoute];

@NgModule({
    imports: [InmarSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SkuComponent, SkuDetailComponent, SkuUpdateComponent, SkuDeleteDialogComponent, SkuDeletePopupComponent, SkuSearchComponent],
    entryComponents: [SkuComponent, SkuUpdateComponent, SkuDeleteDialogComponent, SkuDeletePopupComponent, SkuSearchComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InmarSkuModule {}
