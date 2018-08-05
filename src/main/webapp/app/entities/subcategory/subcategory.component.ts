import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISubcategory } from 'app/shared/model/subcategory.model';
import { Principal } from 'app/core';
import { SubcategoryService } from './subcategory.service';

@Component({
    selector: 'jhi-subcategory',
    templateUrl: './subcategory.component.html'
})
export class SubcategoryComponent implements OnInit, OnDestroy {
    subcategories: ISubcategory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private subcategoryService: SubcategoryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.subcategoryService.query().subscribe(
            (res: HttpResponse<ISubcategory[]>) => {
                this.subcategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSubcategories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISubcategory) {
        return item.id;
    }

    registerChangeInSubcategories() {
        this.eventSubscriber = this.eventManager.subscribe('subcategoryListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
