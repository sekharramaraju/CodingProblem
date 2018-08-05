package inventory.web.rest;

import com.codahale.metrics.annotation.Timed;
import inventory.service.SkuService;
import inventory.web.rest.errors.BadRequestAlertException;
import inventory.web.rest.util.HeaderUtil;
import inventory.service.dto.SkuDTO;
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
 * REST controller for managing Sku.
 */
@RestController
@RequestMapping("/api")
public class SkuResource {

    private final Logger log = LoggerFactory.getLogger(SkuResource.class);

    private static final String ENTITY_NAME = "sku";

    private final SkuService skuService;

    public SkuResource(SkuService skuService) {
        this.skuService = skuService;
    }

    /**
     * POST  /skus : Create a new sku.
     *
     * @param skuDTO the skuDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skuDTO, or with status 400 (Bad Request) if the sku has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skus")
    @Timed
    public ResponseEntity<SkuDTO> createSku(@Valid @RequestBody SkuDTO skuDTO) throws URISyntaxException {
        log.debug("REST request to save Sku : {}", skuDTO);
        if (skuDTO.getId() != null) {
            throw new BadRequestAlertException("A new sku cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkuDTO result = skuService.save(skuDTO);
        return ResponseEntity.created(new URI("/api/skus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skus : Updates an existing sku.
     *
     * @param skuDTO the skuDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skuDTO,
     * or with status 400 (Bad Request) if the skuDTO is not valid,
     * or with status 500 (Internal Server Error) if the skuDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skus")
    @Timed
    public ResponseEntity<SkuDTO> updateSku(@Valid @RequestBody SkuDTO skuDTO) throws URISyntaxException {
        log.debug("REST request to update Sku : {}", skuDTO);
        if (skuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SkuDTO result = skuService.save(skuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skuDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skus : get all the skus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of skus in body
     */
    @GetMapping("/skus")
    @Timed
    public List<SkuDTO> getAllSkus() {
        log.debug("REST request to get all Skus");
        return skuService.findAll();
    }

    /**
     * GET  /skus/:id : get the "id" sku.
     *
     * @param id the id of the skuDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skuDTO, or with status 404 (Not Found)
     */
    @GetMapping("/skus/{id}")
    @Timed
    public ResponseEntity<SkuDTO> getSku(@PathVariable Long id) {
        log.debug("REST request to get Sku : {}", id);
        Optional<SkuDTO> skuDTO = skuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skuDTO);
    }

    /**
     * DELETE  /skus/:id : delete the "id" sku.
     *
     * @param id the id of the skuDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skus/{id}")
    @Timed
    public ResponseEntity<Void> deleteSku(@PathVariable Long id) {
        log.debug("REST request to delete Sku : {}", id);
        skuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
