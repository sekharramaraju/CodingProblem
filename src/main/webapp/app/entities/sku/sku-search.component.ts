import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISku } from 'app/shared/model/sku.model';
import { ISearch } from 'app/shared/model/search.model';
import { Principal } from 'app/core';
import { SkuService } from './sku.service';

@Component({
    selector: 'jhi-sku-search',
    templateUrl: './sku-search.component.html'
})
export class SkuSearchComponent implements OnInit, OnDestroy {
    skus: ISku[];
    currentAccount: any;
    eventSubscriber: Subscription;
    searchSkus: ISearch;

    constructor(
        private skuService: SkuService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
        this.searchSkus = {};
    }

    loadAll() {
        this.skuService.query().subscribe(
            (res: HttpResponse<ISku[]>) => {
                this.skus = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSkus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }
    trackId(index: number, item: ISku) {
        return item.id;
    }

    registerChangeInSkus() {
        this.eventSubscriber = this.eventManager.subscribe('skuListModification', response => this.loadAll());
    }

    search() {
        this.loadAll();
    }
    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
