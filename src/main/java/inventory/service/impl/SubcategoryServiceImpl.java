package inventory.service.impl;

import inventory.service.SubcategoryService;
import inventory.domain.Subcategory;
import inventory.repository.SubcategoryRepository;
import inventory.service.dto.SubcategoryDTO;
import inventory.service.mapper.SubcategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Subcategory.
 */
@Service
@Transactional
public class SubcategoryServiceImpl implements SubcategoryService {

    private final Logger log = LoggerFactory.getLogger(SubcategoryServiceImpl.class);

    private final SubcategoryRepository subcategoryRepository;

    private final SubcategoryMapper subcategoryMapper;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, SubcategoryMapper subcategoryMapper) {
        this.subcategoryRepository = subcategoryRepository;
        this.subcategoryMapper = subcategoryMapper;
    }

    /**
     * Save a subcategory.
     *
     * @param subcategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SubcategoryDTO save(SubcategoryDTO subcategoryDTO) {
        log.debug("Request to save Subcategory : {}", subcategoryDTO);
        Subcategory subcategory = subcategoryMapper.toEntity(subcategoryDTO);
        subcategory = subcategoryRepository.save(subcategory);
        return subcategoryMapper.toDto(subcategory);
    }

    /**
     * Get all the subcategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SubcategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Subcategories");
        return subcategoryRepository.findAll(pageable)
            .map(subcategoryMapper::toDto);
    }


    /**
     * Get one subcategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SubcategoryDTO> findOne(Long id) {
        log.debug("Request to get Subcategory : {}", id);
        return subcategoryRepository.findById(id)
            .map(subcategoryMapper::toDto);
    }

    /**
     * Delete the subcategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Subcategory : {}", id);
        subcategoryRepository.deleteById(id);
    }
}
