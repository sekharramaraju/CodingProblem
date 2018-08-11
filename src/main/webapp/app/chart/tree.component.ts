import { Component, OnInit, OnDestroy,ElementRef,ViewChild,ViewEncapsulation  } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Principal } from 'app/core';
import  *  as d3 from 'd3';
import { HierarchyPointNode } from 'd3';
import { TreeNode } from './../shared/model/treenode.model';

@Component({
    selector: 'jhi-chart-tree',
    templateUrl: './tree.component.html',
    styleUrls: ["chart.style.css"],
    providers: [],
    encapsulation: ViewEncapsulation.None,
})
export class TreeChartComponent implements OnInit, OnDestroy {
    @ViewChild('treechart')
    chartElement: ElementRef;

    parseDate = d3.timeParse('%d-%m-%Y');
    margin = {top: 20, right: 120, bottom: 20, left: 120};
    width = 960 - this.margin.right - this.margin.left;
    height = 800 - this.margin.top - this.margin.bottom;
    
    private svg;
    private treeLayout;
    private root;
    private tree;
    private tableData =[
        {"locationName": "Perimeter", "departmentName": "Bakery", "categoryName": "Bakery Bread", "subcategoryName" : "Bagels"},
        {"locationName": "Perimeter", "departmentName": "Bakery", "categoryName": "Bakery Bread", "subcategoryName" : "Baking or Breading Products"},
        {"locationName": "Center",    "departmentName": "Dairy", "categoryName": "Cheese", "subcategoryName" : "Cheese Sauce"},
    ]
    
    treeJson: TreeNode;

    currentAccount: any;
    eventSubscriber: Subscription;
    data : any[];
    //  data = {
    //     "name": "A1",
    //     "children": [
    //       {
    //         "name": "B1",
    //         "children": [
    //           {
    //             "name": "C1",
    //             "value": 100
    //           },
    //           {
    //             "name": "C2",
    //             "value": 300
    //           },
    //           {
    //             "name": "C3",
    //             "value": 200
    //           }
    //         ]
    //       },
    //       {
    //         "name": "B2",
    //         "value": 200
    //       }
    //     ]
    //   };


    constructor(
        // private categoryService: CategoryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });

        this.setDefaultValuesToTreeData();
        this.convertToJson();
        this.buildChart();
     }

    ngOnDestroy() {
        //this.eventManager.destroy(this.eventSubscriber);
    }

    private onError(errorMessage: string) {
        //this.jhiAlertService.error(errorMessage, null, null);
    }

    buildChart()
    {
        this.root = d3.hierarchy(this.treeJson, (d) => d.children);;

        this.root.x0 = this.height / 2;
        this.root.y0 = 0;
       
        this.svg = d3.select(this.chartElement.nativeElement).append("svg")
                    .attr("width", this.width + this.margin.right + this.margin.left)
                    .attr("height", this.height + this.margin.top + this.margin.bottom)
                    .append("g")
                    .attr("transform", "translate(" + this.margin.left + "," + this.margin.top + ")");

        this.treeLayout = d3.tree().size([this.height, this.width]);

        let collapse = function (d) {
            if (d.children) {
                d._children = d.children;
                d._children.forEach(collapse);
                d.children = null;
            }
        }    
        // this.root.children.forEach(collapse);

        this.update(this.root);
    }

    update(source) {
        let i = 0;
        let duration = 750;
    
        let treeData = this.treeLayout(this.root);
        let nodes = treeData.descendants();
        let links = treeData.descendants().slice(1);
    
        nodes.forEach(d => d.y = d.depth * 180);
    
        let node = this.svg.selectAll("g.node")
            .data(nodes, d =>  d.id || (d.id = ++i) );
    
        let nodeEnter = node.enter().append("g")
            .attr("class", "node")
            .attr("transform", d => "translate(" + source.y0 + "," + source.x0 + ")")
            .on("click", this.click.bind(this));
    
        nodeEnter.append("circle")
            .attr("r", 10)
            .style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });
             
        nodeEnter.append("text")
            .attr("x", function(d) { return d.children || d._children ? -10 : 10; })
            .attr("dy", ".35em")
            .attr("text-anchor", function(d) { return d.children || d._children ? "end" : "start"; })
            .text(function(d) { 
                console.log("node name " + d.data.name);
                return " " + d.data.name +" "; 
            })
            .style("fill-opacity", 10);

        let nodeUpdate = nodeEnter.merge(node);
    
        nodeUpdate.transition()
            .duration(duration)
            .attr("transform", d => "translate(" + d.y + "," + d.x + ")");
    
        nodeUpdate.select("circle.node")
            .attr("r", 10)
            .style("fill", d => d._children ? "lightsteelblue" : "#fff")
            .attr("cursor", "pointer");
    
        let nodeExit = node.exit().transition()
            .duration(duration)
            .attr("transform", d => "translate(" + source.y + "," + source.x + ")")
            .remove();
    
        nodeExit.select("circle")
            .attr("r", 1e-6);
    
        nodeExit.select("text")
            .style("fill-opacity", 1e-6);
    
        let link = this.svg.selectAll("path.link")
            .data(links, d => d.id);
    
        let linkEnter = link.enter().insert("path", "g")
            .attr("class", "link")
            .attr("d", d => {
                let o = { x: source.x0, y: source.y0 };
                return this.diagonal(o, o);
            });
    
        let linkUpdate = linkEnter.merge(link);
    
        linkUpdate.transition()
            .duration(duration)
            .attr("d", d => {
                return this.diagonal(d, d.parent)
            });
    
        let linkExit = link.exit().transition()
            .duration(duration)
            .attr("d", d => {
                let o = { x: source.x, y: source.y };
                return this.diagonal(o, o);
            })
            .remove();
    
        nodes.forEach(d => {
            d.x0 = d.x;
            d.y0 = d.y;
        });
    
    }
    
    click(d) {
        if (d.children) {
            d._children = d.children;
            d.children = null;
        } else {
            d.children = d._children;
            d._children = null;
        }
    
        this.update(d);
    }
    
    diagonal(s, d) {
        let path = `M ${s.y} ${s.x}
                C ${(s.y + d.y) / 2} ${s.x},
                  ${(s.y + d.y) / 2} ${d.x},
                  ${d.y} ${d.x}`
    
        return path;
    }

    convertToJson() {
       this.data = this.tableData.map(this.createJsonData.bind(this));
    }

    setDefaultValuesToTreeData()
    {
        let locationNode : TreeNode;
        let categoryNode : TreeNode;
        let departmentNode : TreeNode;
        let subcategoryNode : TreeNode;

        this.treeJson = new TreeNode();
        this.treeJson.name = "_MASTER_HIERARCHY";

        locationNode = new TreeNode();
        locationNode.name = "Location";

        departmentNode = new TreeNode()
        departmentNode.name = "Department";

        categoryNode = new TreeNode()
        categoryNode.name = "Category";

        subcategoryNode = new TreeNode();
        subcategoryNode.name = "SubCategory";

        categoryNode.children.push(subcategoryNode);
        departmentNode.children.push(categoryNode);
        locationNode.children.push(departmentNode);
        this.treeJson.children.push(locationNode);
    }
    createJsonData(row)
    {
        let locationNode = this.treeJson.children.find( function(d) { return d.name == row.locationName;});
        if (locationNode == null){
            locationNode = this.addNewLocation(row);
            this.treeJson.children.push(locationNode);
        }
        else
        {
            let departmentNode = locationNode.children.find (function(d) { return d.name == row.departmentName;});
            if (departmentNode == null){
                departmentNode = this.addNewDepartment(row);
                locationNode.children.push(departmentNode);
            }
            else
            {
                let categoryNode = departmentNode.children.find( function (d) { return d.name == row.categoryName; });
                if (categoryNode == null) {
                    categoryNode = this.addNewCategoryNode(row);
                    departmentNode.children.push(categoryNode);
                }
                else
                {
                    let subcategoryNode = categoryNode.children.find( function (d) { return d.name == row.subcategoryName;});
                    if (subcategoryNode == null)
                    {
                        subcategoryNode = this.addNewSubCategoryNode(row);
                        categoryNode.children.push(subcategoryNode);
                    }
                }
            }

        }

        console.log ("dd is  " + row.locationName);
        return row
    } 

    addNewLocation(row)
    {
        let location = new TreeNode();
        location.name = row.locationName;
        location.children.push(this.addNewDepartment(row));
        return location;
    }

    addNewDepartment(row)
    {
        let dept = new TreeNode();
        dept.name = row.departmentName;
        dept.children.push(this.addNewCategoryNode(row));
        return dept;
    }

    addNewCategoryNode(row) {
        let category = new TreeNode();
        category.name = row.categoryName;
        category.children.push(this.addNewSubCategoryNode(row));
        return category;
    }

    addNewSubCategoryNode(row) {
        let subcate = new TreeNode();
        subcate.name = row.subcategoryName;
        return subcate;
    }

}
