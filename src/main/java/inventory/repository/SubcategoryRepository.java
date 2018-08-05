package inventory.repository;

import inventory.domain.Category;
import inventory.domain.Subcategory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


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
}
