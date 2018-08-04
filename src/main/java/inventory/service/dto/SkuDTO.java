package inventory.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Sku entity.
 */
public class SkuDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 4, max = 50)
    private String skuid;

    @NotNull
    @Size(min = 4, max = 50)
    private String name;

    @NotNull
    @Size(min = 4, max = 50)
    private String barcode;

    @NotNull
    private String status;

    private Long subcategoryId;

    private String subcategoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SkuDTO skuDTO = (SkuDTO) o;
        if (skuDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skuDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkuDTO{" +
            "id=" + getId() +
            ", skuid='" + getSkuid() + "'" +
            ", name='" + getName() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", status='" + getStatus() + "'" +
            ", subcategory=" + getSubcategoryId() +
            ", subcategory='" + getSubcategoryName() + "'" +
            "}";
    }
}
