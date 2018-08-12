export interface ITreechartData {
    locationName?: string;
    departmentName?: string;
    categoryName?: string;
    subcategoryName?: string;
}

export class TreecharData implements ITreechartData {
    constructor(
        public locationName?: string,
        public departmentName?: string,
        public categoryName?: string,
        public subcategoryName?: string
    ) {}
}