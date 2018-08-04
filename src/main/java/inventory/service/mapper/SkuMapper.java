package inventory.service.mapper;

import inventory.domain.*;
import inventory.service.dto.SkuDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sku and its DTO SkuDTO.
 */
@Mapper(componentModel = "spring", uses = {SubcategoryMapper.class})
public interface SkuMapper extends EntityMapper<SkuDTO, Sku> {

    @Mapping(source = "subcategory.id", target = "subcategoryId")
    @Mapping(source = "subcategory.name", target = "subcategoryName")
    SkuDTO toDto(Sku sku);

    @Mapping(source = "subcategoryId", target = "subcategory")
    Sku toEntity(SkuDTO skuDTO);

    default Sku fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sku sku = new Sku();
        sku.setId(id);
        return sku;
    }
}
