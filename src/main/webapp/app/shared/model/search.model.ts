export interface ISearch {
    locationName?: string;
    departmentName?: string;
    categoryName?: string;
    subcategoryName?: string;
}

export class Search implements ISearch {
    constructor(
        public locationName?: string,
        public departmentName?: string,
        public categoryName?: string,
        public subcategoryName?: string
    ) {}
}
