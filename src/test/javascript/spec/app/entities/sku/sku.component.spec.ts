/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InmarTestModule } from '../../../test.module';
import { SkuComponent } from 'app/entities/sku/sku.component';
import { SkuService } from 'app/entities/sku/sku.service';
import { Sku } from 'app/shared/model/sku.model';

describe('Component Tests', () => {
    describe('Sku Management Component', () => {
        let comp: SkuComponent;
        let fixture: ComponentFixture<SkuComponent>;
        let service: SkuService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [InmarTestModule],
                declarations: [SkuComponent],
                providers: []
            })
                .overrideTemplate(SkuComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SkuComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkuService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Sku(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.skus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
