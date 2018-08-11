package inventory.web.rest;

import com.codahale.metrics.annotation.Timed;

import inventory.service.ChartService;
import inventory.service.SubcategoryService;
import inventory.web.rest.errors.BadRequestAlertException;
import inventory.web.rest.util.HeaderUtil;
import inventory.service.dto.SubcategoryDTO;
import inventory.service.dto.TreeChartDTO;
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
 * REST controller for managing chart data.
 */
@RestController
@RequestMapping("/api")
public class ChartResource {

	 private final Logger log = LoggerFactory.getLogger(ChartResource.class);

    private static final String ENTITY_NAME = "subcategory";

    private final ChartService chartService;

    public ChartResource(ChartService chartService) {
        this.chartService = chartService;
    }

    /**
     * GET  /subcategories : get all the subcategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subcategories in body
     */
    @GetMapping("/treechartdata")
    @Timed
    public List<TreeChartDTO> getAllDataForTreeChart() {
        log.debug("REST request to get all getAllDataForTreeChart");
        return chartService.findAllForTreeChart();
    }
   
}
