package inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Sku.
 */
@Entity
@Table(name = "sku")
public class Sku implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "skuid", length = 50, nullable = false)
    private String skuid;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "barcode", length = 50, nullable = false)
    private String barcode;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Subcategory subcategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuid() {
        return skuid;
    }

    public Sku skuid(String skuid) {
        this.skuid = skuid;
        return this;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public String getName() {
        return name;
    }

    public Sku name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public Sku barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getStatus() {
        return status;
    }

    public Sku status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public Sku subcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
        return this;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sku sku = (Sku) o;
        if (sku.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sku.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sku{" +
            "id=" + getId() +
            ", skuid='" + getSkuid() + "'" +
            ", name='" + getName() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
