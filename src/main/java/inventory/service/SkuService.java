package inventory.service;

import inventory.service.dto.SkuDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Sku.
 */
public interface SkuService {

    /**
     * Save a sku.
     *
     * @param skuDTO the entity to save
     * @return the persisted entity
     */
    SkuDTO save(SkuDTO skuDTO);

    /**
     * Get all the skus.
     *
     * @return the list of entities
     */
    List<SkuDTO> findAll();


    /**
     * Get the "id" sku.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SkuDTO> findOne(Long id);

    /**
     * Delete the "id" sku.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
