import { NgModule } from '@angular/core';

import { InmarSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [InmarSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [InmarSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class InmarSharedCommonModule {}
