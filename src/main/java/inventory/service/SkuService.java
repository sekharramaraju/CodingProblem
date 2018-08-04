package inventory.service;

import inventory.service.dto.SkuDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SkuDTO> findAll(Pageable pageable);


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
