package inventory.service.dto;

import java.io.Serializable;

/*
 * DTO for Tree chart component
 */
public class TreeChartDTO implements Serializable {
	
	private String locationName;
	
	private String departmentName;
	
	private String categroyName;
	
	private String subcategroyName;
	
	public TreeChartDTO( String subcategory, String category, String department, String location){
		this.locationName = location;
		this.departmentName = department;
		this. categroyName = category;
		this.subcategroyName = subcategory;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getCategroyName() {
		return categroyName;
	}

	public void setCategroyName(String categroyName) {
		this.categroyName = categroyName;
	}

	public String getSubcategroyName() {
		return subcategroyName;
	}

	public void setSubcategroyName(String subcategroyName) {
		this.subcategroyName = subcategroyName;
	}
	
	
}
