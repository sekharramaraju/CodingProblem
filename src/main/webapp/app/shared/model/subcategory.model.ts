export interface ISubcategory {
    id?: number;
    name?: string;
    status?: string;
    categoryName?: string;
    categoryId?: number;
}

export class Subcategory implements ISubcategory {
    constructor(
        public id?: number,
        public name?: string,
        public status?: string,
        public categoryName?: string,
        public categoryId?: number
    ) {}
}
