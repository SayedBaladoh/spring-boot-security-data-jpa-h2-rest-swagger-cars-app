package de.evolvice.cars.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.evolvice.cars.entity.Specification;
/**
 * Specification Repository
 * 
 * @author sayed
 *
 */
@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Long> {

	Page<Specification> findByModelBrandId(Long brandId, Pageable pageable);

	Page<Specification> findByModelId(Long modelId, Pageable pageable);

	Page<Specification> findByModelBrandIdAndModelId(Long brandId, Long modelId, Pageable pageable);

	Page<Specification> findByBodyTypeId(Long bodyTypeId, Pageable pageable);

	Optional<Specification> findByIdAndModelBrandId(Long id, Long brandId);

	Optional<Specification> findByIdAndModelId(Long id, Long modelId);

	Optional<Specification> findByIdAndModelBrandIdAndModelId(Long id, Long brandId, Long modelId);

}
