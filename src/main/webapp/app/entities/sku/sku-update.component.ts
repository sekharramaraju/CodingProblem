import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISku } from 'app/shared/model/sku.model';
import { SkuService } from './sku.service';
import { ISubcategory } from 'app/shared/model/subcategory.model';
import { SubcategoryService } from 'app/entities/subcategory';

@Component({
    selector: 'jhi-sku-update',
    templateUrl: './sku-update.component.html'
})
export class SkuUpdateComponent implements OnInit {
    private _sku: ISku;
    isSaving: boolean;

    subcategories: ISubcategory[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private skuService: SkuService,
        private subcategoryService: SubcategoryService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sku }) => {
            this.sku = sku;
        });
        this.subcategoryService.query().subscribe(
            (res: HttpResponse<ISubcategory[]>) => {
                this.subcategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sku.id !== undefined) {
            this.subscribeToSaveResponse(this.skuService.update(this.sku));
        } else {
            this.subscribeToSaveResponse(this.skuService.create(this.sku));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISku>>) {
        result.subscribe((res: HttpResponse<ISku>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSubcategoryById(index: number, item: ISubcategory) {
        return item.id;
    }
    get sku() {
        return this._sku;
    }

    set sku(sku: ISku) {
        this._sku = sku;
    }
}
