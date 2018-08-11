import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InmarSharedModule } from 'app/shared';
import { TreeChartComponent } from './tree.component';
import { chartRoute } from './chart.route';

const ENTITY_STATES = [...chartRoute];

@NgModule({
    imports: [InmarSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TreeChartComponent],
    entryComponents: [TreeChartComponent],
    schemas: []
})
export class InmarChartModule {}
