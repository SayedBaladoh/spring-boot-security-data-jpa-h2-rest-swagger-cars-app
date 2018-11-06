package de.evolvice.cars.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.evolvice.cars.entity.Model;
/**
 * Model Repository
 * 
 * @author sayed
 *
 */
@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

	Page<Model> findByBrandId(Long prandId, Pageable pageable);

	Optional<Model> findByIdAndBrandId(Long id, Long prandId);

}
