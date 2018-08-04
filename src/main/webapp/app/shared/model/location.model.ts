export interface ILocation {
    id?: number;
    name?: string;
    status?: string;
}

export class Location implements ILocation {
    constructor(public id?: number, public name?: string, public status?: string) {}
}
