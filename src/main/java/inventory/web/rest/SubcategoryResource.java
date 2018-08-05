package inventory.web.rest;

import com.codahale.metrics.annotation.Timed;
import inventory.service.SubcategoryService;
import inventory.web.rest.errors.BadRequestAlertException;
import inventory.web.rest.util.HeaderUtil;
import inventory.service.dto.SubcategoryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Subcategory.
 */
@RestController
@RequestMapping("/api")
public class SubcategoryResource {

    private final Logger log = LoggerFactory.getLogger(SubcategoryResource.class);

    private static final String ENTITY_NAME = "subcategory";

    private final SubcategoryService subcategoryService;

    public SubcategoryResource(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    /**
     * POST  /subcategories : Create a new subcategory.
     *
     * @param subcategoryDTO the subcategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subcategoryDTO, or with status 400 (Bad Request) if the subcategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/subcategories")
    @Timed
    public ResponseEntity<SubcategoryDTO> createSubcategory(@Valid @RequestBody SubcategoryDTO subcategoryDTO) throws URISyntaxException {
        log.debug("REST request to save Subcategory : {}", subcategoryDTO);
        if (subcategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new subcategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubcategoryDTO result = subcategoryService.save(subcategoryDTO);
        return ResponseEntity.created(new URI("/api/subcategories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subcategories : Updates an existing subcategory.
     *
     * @param subcategoryDTO the subcategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subcategoryDTO,
     * or with status 400 (Bad Request) if the subcategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the subcategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/subcategories")
    @Timed
    public ResponseEntity<SubcategoryDTO> updateSubcategory(@Valid @RequestBody SubcategoryDTO subcategoryDTO) throws URISyntaxException {
        log.debug("REST request to update Subcategory : {}", subcategoryDTO);
        if (subcategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubcategoryDTO result = subcategoryService.save(subcategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subcategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subcategories : get all the subcategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subcategories in body
     */
    @GetMapping("/subcategories")
    @Timed
    public List<SubcategoryDTO> getAllSubcategories() {
        log.debug("REST request to get all Subcategories");
        return subcategoryService.findAll();
    }

    /**
     * GET  /subcategories/:id : get the "id" subcategory.
     *
     * @param id the id of the subcategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subcategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/subcategories/{id}")
    @Timed
    public ResponseEntity<SubcategoryDTO> getSubcategory(@PathVariable Long id) {
        log.debug("REST request to get Subcategory : {}", id);
        Optional<SubcategoryDTO> subcategoryDTO = subcategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subcategoryDTO);
    }

    /**
     * DELETE  /subcategories/:id : delete the "id" subcategory.
     *
     * @param id the id of the subcategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/subcategories/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Long id) {
        log.debug("REST request to delete Subcategory : {}", id);
        subcategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * GET  /subcategories : get all the subcategories by parent params
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subcategories in body
     */
    @GetMapping("/locations/{locationId}/departments/{departmentId}/categories/{categoryId}/subcategories")
    @Timed
    public List<SubcategoryDTO> getAllSubcategoriesByParentIds(@PathVariable Long locationId, @PathVariable Long departmentId, @PathVariable Long categoryId) {
        log.debug("REST request to get all Subcategories");
        return subcategoryService.findAllByLocationAndDepartmentAndCategory(locationId, departmentId, categoryId);
    }

    /**
     * GET  /subcategories/:id : get the "id" subcategory by parent params
     *
     * @param id the id of the subcategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subcategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/locations/{locationId}/departments/{departmentId}/categories/{categoryId}/subcategories/{id}")
    @Timed
    public ResponseEntity<SubcategoryDTO> ByParentIds(@PathVariable Long locationId, @PathVariable Long departmentId, @PathVariable Long categoryId,@PathVariable Long id) {
        log.debug("REST request to get Subcategory : {}", id);
        Optional<SubcategoryDTO> subcategoryDTO = subcategoryService.findOneByLocationAndDepartmentAndCategory(locationId, departmentId, categoryId,id);
        return ResponseUtil.wrapOrNotFound(subcategoryDTO);
    }
}
