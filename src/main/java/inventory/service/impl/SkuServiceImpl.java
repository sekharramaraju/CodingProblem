package inventory.service.impl;

import inventory.service.SkuService;
import inventory.domain.Sku;
import inventory.repository.SkuRepository;
import inventory.service.dto.SearchRequestDTO;
import inventory.service.dto.SkuDTO;
import inventory.service.mapper.SkuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SkuDTO> findAll() {
        log.debug("Request to get all Skus");
        return skuRepository.findAll().stream()
            .map(skuMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
    
    /**
     * Get all the skus.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SkuDTO> findAllBySearchParams(SearchRequestDTO searchRequestDTO) {
        log.debug("Request to get all Skus");
        
        String locationName = "%";
        String departmentName = "%";
        String categoryName = "%";
        String subcategoryName = "%";
        
        if(( searchRequestDTO.getLocationName() != null) && (0 != searchRequestDTO.getLocationName().compareTo("null")))
        {
        	locationName = "%" + searchRequestDTO.getLocationName() + "%";
        }
        
        if((searchRequestDTO.getDepartmentName() != null) && (0 != searchRequestDTO.getDepartmentName().compareTo("null")))
        {
        	departmentName = "%" + searchRequestDTO.getDepartmentName() + "%";
        }
        
        if((searchRequestDTO.getCategoryName() != null) && (0 != searchRequestDTO.getCategoryName().compareTo("null")))
        {
        	categoryName = "%" + searchRequestDTO.getCategoryName() + "%";
        }
        
        if((searchRequestDTO.getSubcategoryName() != null) && (0 != searchRequestDTO.getSubcategoryName().compareTo("null")))
        {
        	subcategoryName = "%" + searchRequestDTO.getSubcategoryName() + "%";
        }
        
        String qu = "SELECT sk FROM Sku sk, Subcategory su  where su.id = sk.subcategory.id and sk.subcategory.name like" + subcategoryName + "and sk.subcategory.category.name like :" + categoryName + "and sk.subcategory.category.department.name like :" + departmentName +" and sk.subcategory.category.department.location.name like :" + locationName + ")";

        log.debug("Query is "+ qu);
        
        return skuRepository.findAllBySearchPrams(locationName, departmentName, categoryName, subcategoryName).stream()
            .map(skuMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

}
