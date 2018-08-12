package inventory.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inventory.repository.SubcategoryRepository;
import inventory.service.ChartService;
import inventory.service.dto.TreeChartDTO;


@Service
@Transactional
public class ChatServiceImpl implements ChartService {

	private final Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);
	
	private final SubcategoryRepository subcategoryRepository;
	
	public ChatServiceImpl(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }
	
	@Override
	@Transactional(readOnly = true)
	public List<TreeChartDTO> findAllForTreeChart() {
		 log.debug("Request to get all findAllForTreeChart");
	     return this.subcategoryRepository.findAllForTreeChart();
	}
}
