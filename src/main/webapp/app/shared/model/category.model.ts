export interface ICategory {
    id?: number;
    name?: string;
    status?: string;
    departmentName?: string;
    departmentId?: number;
}

export class Category implements ICategory {
    constructor(
        public id?: number,
        public name?: string,
        public status?: string,
        public departmentName?: string,
        public departmentId?: number
    ) {}
}
