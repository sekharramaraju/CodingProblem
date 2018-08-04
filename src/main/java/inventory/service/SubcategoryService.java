package inventory.service;

import inventory.service.dto.SubcategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Subcategory.
 */
public interface SubcategoryService {

    /**
     * Save a subcategory.
     *
     * @param subcategoryDTO the entity to save
     * @return the persisted entity
     */
    SubcategoryDTO save(SubcategoryDTO subcategoryDTO);

    /**
     * Get all the subcategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SubcategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" subcategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SubcategoryDTO> findOne(Long id);

    /**
     * Delete the "id" subcategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
