/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { InmarTestModule } from '../../../test.module';
import { SkuUpdateComponent } from 'app/entities/sku/sku-update.component';
import { SkuService } from 'app/entities/sku/sku.service';
import { Sku } from 'app/shared/model/sku.model';

describe('Component Tests', () => {
    describe('Sku Management Update Component', () => {
        let comp: SkuUpdateComponent;
        let fixture: ComponentFixture<SkuUpdateComponent>;
        let service: SkuService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [InmarTestModule],
                declarations: [SkuUpdateComponent]
            })
                .overrideTemplate(SkuUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SkuUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkuService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Sku(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sku = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Sku();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sku = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
