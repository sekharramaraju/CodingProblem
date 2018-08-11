import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITreechartData } from 'app/shared/model/treechartdata.model';

type EntityResponseType = HttpResponse<ITreechartData>;
type EntityArrayResponseType = HttpResponse<ITreechartData[]>;

@Injectable({ providedIn: 'root' })
export class ChartService {
    private resourceUrl = SERVER_API_URL + 'api/treechartdata';

    constructor(private http: HttpClient) {}

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITreechartData[]>(this.resourceUrl, { params: options, observe: 'response' });
    }
}
