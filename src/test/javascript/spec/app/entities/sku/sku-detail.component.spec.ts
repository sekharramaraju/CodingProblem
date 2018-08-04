/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InmarTestModule } from '../../../test.module';
import { SkuDetailComponent } from 'app/entities/sku/sku-detail.component';
import { Sku } from 'app/shared/model/sku.model';

describe('Component Tests', () => {
    describe('Sku Management Detail Component', () => {
        let comp: SkuDetailComponent;
        let fixture: ComponentFixture<SkuDetailComponent>;
        const route = ({ data: of({ sku: new Sku(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [InmarTestModule],
                declarations: [SkuDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SkuDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SkuDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sku).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
