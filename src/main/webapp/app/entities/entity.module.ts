import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { InmarLocationModule } from './location/location.module';
import { InmarDepartmentModule } from './department/department.module';
import { InmarCategoryModule } from './category/category.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        InmarLocationModule,
        InmarDepartmentModule,
        InmarCategoryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InmarEntityModule {}
