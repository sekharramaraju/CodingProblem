export class TreeNode  {
    public name?: string;
    public children?: Array<TreeNode> = [];
    constructor(
        name?: string
    ) {
        this.children = [];
    }
}
