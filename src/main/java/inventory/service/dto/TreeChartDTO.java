package inventory.service.dto;

import java.io.Serializable;

/*
 * DTO for Tree chart component
 */
public class TreeChartDTO implements Serializable {
	
	private String locationName;
	
	private String departmentName;
	
	private String categoryName;
	
	private String subcategoryName;
	
	public TreeChartDTO( String subcategory, String category, String department, String location){
		this.locationName = location;
		this.departmentName = department;
		this. categoryName = category;
		this.subcategoryName = subcategory;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubcategoryName() {
		return subcategoryName;
	}

	public void setSubcategroyName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
	
	 @Override
	  public String toString() {
	        return "SubcategoryDTO{" +
	            "locationName=" + getLocationName() +
	            ", departmentName='" + getDepartmentName() + "'" +
	            ", categoryName='" + getCategoryName() + "'" +
	            ", subcategoryName=" + getSubcategoryName() +
	            
	            "}";
	    }
	
}
