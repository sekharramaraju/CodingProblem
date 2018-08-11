import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { InmarSharedModule } from 'app/shared';
import { InmarCoreModule } from 'app/core';
import { InmarAppRoutingModule } from './app-routing.module';
import { InmarHomeModule } from './home/home.module';
import { InmarAccountModule } from './account/account.module';
import { InmarEntityModule } from './entities/entity.module';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { InmarChartModule } from 'app/chart/chart.module';

// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ErrorComponent } from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        InmarAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        InmarSharedModule,
        InmarCoreModule,
        InmarHomeModule,
        InmarAccountModule,
        InmarEntityModule,
        InmarChartModule,
        NgbModule.forRoot()
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [StateStorageService, Injector]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [JhiEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class InmarAppModule {}
