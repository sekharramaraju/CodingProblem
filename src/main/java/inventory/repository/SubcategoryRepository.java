package inventory.repository;

import inventory.domain.Subcategory;
import inventory.service.dto.TreeChartDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data  repository for the Subcategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

	@Query("select s from Subcategory s where s.category.department.location.id=?1 and s.category.department.id = ?2 and s.category.id = ?3")
	List<Subcategory> findAllByLocationAndDepartmentAndCategory(Long locationId, Long departmentId, Long categoryId);
	
	@Query("select s from Subcategory s where s.id = ?4 and s.category.department.location.id=?1 and s.category.department.id = ?2 and s.category.id = ?3")
	Optional<Subcategory> findOneByLocationAndDepartmentAndCategory(Long locationId, Long departmentId, Long categoryId,Long id);
	
	@Query("select NEW inventory.service.dto.TreeChartDTO( s.name, s.category.name,s.category.department.name, s.category.department.location.name) from Subcategory s")
	List<TreeChartDTO> findAllForTreeChart();
}
