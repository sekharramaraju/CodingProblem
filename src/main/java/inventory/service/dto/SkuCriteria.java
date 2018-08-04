package inventory.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Sku entity. This class is used in SkuResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /skus?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SkuCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter skuid;

    private StringFilter name;

    private StringFilter barcode;

    private StringFilter status;

    private LongFilter subcategoryId;

    public SkuCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSkuid() {
        return skuid;
    }

    public void setSkuid(StringFilter skuid) {
        this.skuid = skuid;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getBarcode() {
        return barcode;
    }

    public void setBarcode(StringFilter barcode) {
        this.barcode = barcode;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(LongFilter subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    @Override
    public String toString() {
        return "SkuCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (skuid != null ? "skuid=" + skuid + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (barcode != null ? "barcode=" + barcode + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (subcategoryId != null ? "subcategoryId=" + subcategoryId + ", " : "") +
            "}";
    }

}
