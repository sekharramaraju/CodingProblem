package inventory.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import inventory.domain.Subcategory;
import inventory.domain.*; // for static metamodels
import inventory.repository.SubcategoryRepository;
import inventory.service.dto.SubcategoryCriteria;

import inventory.service.dto.SubcategoryDTO;
import inventory.service.mapper.SubcategoryMapper;

/**
 * Service for executing complex queries for Subcategory entities in the database.
 * The main input is a {@link SubcategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SubcategoryDTO} or a {@link Page} of {@link SubcategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubcategoryQueryService extends QueryService<Subcategory> {

    private final Logger log = LoggerFactory.getLogger(SubcategoryQueryService.class);

    private final SubcategoryRepository subcategoryRepository;

    private final SubcategoryMapper subcategoryMapper;

    public SubcategoryQueryService(SubcategoryRepository subcategoryRepository, SubcategoryMapper subcategoryMapper) {
        this.subcategoryRepository = subcategoryRepository;
        this.subcategoryMapper = subcategoryMapper;
    }

    /**
     * Return a {@link List} of {@link SubcategoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SubcategoryDTO> findByCriteria(SubcategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Subcategory> specification = createSpecification(criteria);
        return subcategoryMapper.toDto(subcategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SubcategoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubcategoryDTO> findByCriteria(SubcategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Subcategory> specification = createSpecification(criteria);
        return subcategoryRepository.findAll(specification, page)
            .map(subcategoryMapper::toDto);
    }

    /**
     * Function to convert SubcategoryCriteria to a {@link Specification}
     */
    private Specification<Subcategory> createSpecification(SubcategoryCriteria criteria) {
        Specification<Subcategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Subcategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Subcategory_.name));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Subcategory_.status));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCategoryId(), Subcategory_.category, Category_.id));
            }
        }
        return specification;
    }

}
