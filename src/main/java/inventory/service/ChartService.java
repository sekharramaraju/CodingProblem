package inventory.service;

import java.util.List;
import java.util.Optional;

import inventory.service.dto.SubcategoryDTO;
import inventory.service.dto.TreeChartDTO;

public interface ChartService {
	
	/**
     * Get all the data for tree chart.
     *
     * @return the list of entities
     */
    List<TreeChartDTO> findAllForTreeChart();
}
