package inventory.repository;

import inventory.domain.Sku;
import inventory.domain.Subcategory;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sku entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkuRepository extends JpaRepository<Sku, Long> {

	@Query("SELECT sk FROM Sku sk, Subcategory su  where su.id = sk.subcategory.id and sk.subcategory.name like :subcategoryName and sk.subcategory.category.name like :categoryName and sk.subcategory.category.department.name like :departmentName and sk.subcategory.category.department.location.name like :locationName order by sk.id")
	List<Sku> findAllBySearchPrams(@Param("locationName") String locationName, @Param("departmentName") String departmentName, @Param("categoryName") String categoryName, @Param("subcategoryName") String subcategoryName);
	
}
