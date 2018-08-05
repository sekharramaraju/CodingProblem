package inventory.repository;

import inventory.domain.Department;
import inventory.service.dto.DepartmentDTO;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Department entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	@Query("select d from Department d where d.location.id = ?1")
	List<Department> findByLocation(Long id);
	
	@Query("select d from Department d where d.id= ?1 and d.location.id = ?2")
	Optional<Department> findOneByIdAndLocation(Long id, Long locationId);
}
