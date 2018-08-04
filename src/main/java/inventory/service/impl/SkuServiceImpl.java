package inventory.service.impl;

import inventory.service.SkuService;
import inventory.domain.Sku;
import inventory.repository.SkuRepository;
import inventory.service.dto.SkuDTO;
import inventory.service.mapper.SkuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Sku.
 */
@Service
@Transactional
public class SkuServiceImpl implements SkuService {

    private final Logger log = LoggerFactory.getLogger(SkuServiceImpl.class);

    private final SkuRepository skuRepository;

    private final SkuMapper skuMapper;

    public SkuServiceImpl(SkuRepository skuRepository, SkuMapper skuMapper) {
        this.skuRepository = skuRepository;
        this.skuMapper = skuMapper;
    }

    /**
     * Save a sku.
     *
     * @param skuDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SkuDTO save(SkuDTO skuDTO) {
        log.debug("Request to save Sku : {}", skuDTO);
        Sku sku = skuMapper.toEntity(skuDTO);
        sku = skuRepository.save(sku);
        return skuMapper.toDto(sku);
    }

    /**
     * Get all the skus.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SkuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Skus");
        return skuRepository.findAll(pageable)
            .map(skuMapper::toDto);
    }


    /**
     * Get one sku by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SkuDTO> findOne(Long id) {
        log.debug("Request to get Sku : {}", id);
        return skuRepository.findById(id)
            .map(skuMapper::toDto);
    }

    /**
     * Delete the sku by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sku : {}", id);
        skuRepository.deleteById(id);
    }
}
