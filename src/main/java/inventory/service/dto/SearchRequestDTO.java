package inventory.service.dto;

import java.io.Serializable;

public class SearchRequestDTO implements Serializable {

	private String locationName;
	
	private String departmentName;
	
	private String categoryName;
	
	private String subcategoryName;

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

		
	@Override
    public String toString() {
        return "SearchRequestDTO{" +
            "locationName=" + getLocationName() +
            ", departmentName='" + getDepartmentName() + "'" +
            ", categoryName='" + getCategoryName() + "'" +
            ", subCategoryName=" + getSubcategoryName() +
            "}";
    }

	public String getSubcategoryName() {
		return subcategoryName;
	}

	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
	
	
}
