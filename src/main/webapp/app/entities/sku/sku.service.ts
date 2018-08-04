import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISku } from 'app/shared/model/sku.model';

type EntityResponseType = HttpResponse<ISku>;
type EntityArrayResponseType = HttpResponse<ISku[]>;

@Injectable({ providedIn: 'root' })
export class SkuService {
    private resourceUrl = SERVER_API_URL + 'api/skus';

    constructor(private http: HttpClient) {}

    create(sku: ISku): Observable<EntityResponseType> {
        return this.http.post<ISku>(this.resourceUrl, sku, { observe: 'response' });
    }

    update(sku: ISku): Observable<EntityResponseType> {
        return this.http.put<ISku>(this.resourceUrl, sku, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISku>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISku[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
