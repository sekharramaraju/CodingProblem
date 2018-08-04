import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InmarSharedModule } from 'app/shared';
import {
    SubcategoryComponent,
    SubcategoryDetailComponent,
    SubcategoryUpdateComponent,
    SubcategoryDeletePopupComponent,
    SubcategoryDeleteDialogComponent,
    subcategoryRoute,
    subcategoryPopupRoute
} from './';

const ENTITY_STATES = [...subcategoryRoute, ...subcategoryPopupRoute];

@NgModule({
    imports: [InmarSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SubcategoryComponent,
        SubcategoryDetailComponent,
        SubcategoryUpdateComponent,
        SubcategoryDeleteDialogComponent,
        SubcategoryDeletePopupComponent
    ],
    entryComponents: [SubcategoryComponent, SubcategoryUpdateComponent, SubcategoryDeleteDialogComponent, SubcategoryDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InmarSubcategoryModule {}
