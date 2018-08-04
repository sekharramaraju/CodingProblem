import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISku } from 'app/shared/model/sku.model';

@Component({
    selector: 'jhi-sku-detail',
    templateUrl: './sku-detail.component.html'
})
export class SkuDetailComponent implements OnInit {
    sku: ISku;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sku }) => {
            this.sku = sku;
        });
    }

    previousState() {
        window.history.back();
    }
}
