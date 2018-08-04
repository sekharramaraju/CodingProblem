/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { InmarTestModule } from '../../../test.module';
import { SkuDeleteDialogComponent } from 'app/entities/sku/sku-delete-dialog.component';
import { SkuService } from 'app/entities/sku/sku.service';

describe('Component Tests', () => {
    describe('Sku Management Delete Component', () => {
        let comp: SkuDeleteDialogComponent;
        let fixture: ComponentFixture<SkuDeleteDialogComponent>;
        let service: SkuService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [InmarTestModule],
                declarations: [SkuDeleteDialogComponent]
            })
                .overrideTemplate(SkuDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SkuDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkuService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
