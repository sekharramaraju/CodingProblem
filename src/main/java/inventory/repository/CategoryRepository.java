package inventory.repository;

import inventory.domain.Category;
import inventory.domain.Department;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	@Query("select c from Category c where c.department.location.id = ?1 and c.department.id = ?2")
	List<Category> findAllByLocationAndDepartment(Long locattionId, Long departmentId);
	
	@Query("select c from Category c where c.id =?3 and c.department.location.id = ?1 and c.department.id = ?2 ")
	Optional<Category> findOneByIdAndLocationAndDepartment(Long locattionId, Long departmentId, Long id);

}
