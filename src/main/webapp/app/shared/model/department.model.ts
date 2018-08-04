export interface IDepartment {
    id?: number;
    name?: string;
    status?: string;
    locationName?: string;
    locationId?: number;
}

export class Department implements IDepartment {
    constructor(
        public id?: number,
        public name?: string,
        public status?: string,
        public locationName?: string,
        public locationId?: number
    ) {}
}
