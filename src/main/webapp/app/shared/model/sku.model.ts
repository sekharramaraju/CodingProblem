export interface ISku {
    id?: number;
    skuid?: string;
    name?: string;
    barcode?: string;
    status?: string;
    subcategoryName?: string;
    subcategoryId?: number;
}

export class Sku implements ISku {
    constructor(
        public id?: number,
        public skuid?: string,
        public name?: string,
        public barcode?: string,
        public status?: string,
        public subcategoryName?: string,
        public subcategoryId?: number
    ) {}
}
