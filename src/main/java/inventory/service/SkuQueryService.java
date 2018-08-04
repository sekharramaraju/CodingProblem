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

import inventory.domain.Sku;
import inventory.domain.*; // for static metamodels
import inventory.repository.SkuRepository;
import inventory.service.dto.SkuCriteria;

import inventory.service.dto.SkuDTO;
import inventory.service.mapper.SkuMapper;

/**
 * Service for executing complex queries for Sku entities in the database.
 * The main input is a {@link SkuCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SkuDTO} or a {@link Page} of {@link SkuDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkuQueryService extends QueryService<Sku> {

    private final Logger log = LoggerFactory.getLogger(SkuQueryService.class);

    private final SkuRepository skuRepository;

    private final SkuMapper skuMapper;

    public SkuQueryService(SkuRepository skuRepository, SkuMapper skuMapper) {
        this.skuRepository = skuRepository;
        this.skuMapper = skuMapper;
    }

    /**
     * Return a {@link List} of {@link SkuDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SkuDTO> findByCriteria(SkuCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Sku> specification = createSpecification(criteria);
        return skuMapper.toDto(skuRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SkuDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SkuDTO> findByCriteria(SkuCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Sku> specification = createSpecification(criteria);
        return skuRepository.findAll(specification, page)
            .map(skuMapper::toDto);
    }

    /**
     * Function to convert SkuCriteria to a {@link Specification}
     */
    private Specification<Sku> createSpecification(SkuCriteria criteria) {
        Specification<Sku> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Sku_.id));
            }
            if (criteria.getSkuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSkuid(), Sku_.skuid));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Sku_.name));
            }
            if (criteria.getBarcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBarcode(), Sku_.barcode));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Sku_.status));
            }
            if (criteria.getSubcategoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSubcategoryId(), Sku_.subcategory, Subcategory_.id));
            }
        }
        return specification;
    }

}
