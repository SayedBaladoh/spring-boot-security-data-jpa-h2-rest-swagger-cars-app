package de.evolvice.cars.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import de.evolvice.cars.entity.Specification;

/**
 * Body Specification Service
 * 
 * @author SayedBaladoh
 *
 */
public interface SpecificationService {

	public Page<Specification> getAll(Pageable pageable);
	
	public Page<Specification> getAll(Long brandId, Pageable pageable);
	
	public Page<Specification> getAll(Pageable pageable, Long modelId);
	
	public Page<Specification> getAll(Long brandId, Long modelId, Pageable pageable);

	public Page<Specification> getAllByBodyTypeId(Long bodyTypeId, Pageable pageable);

	public Optional<Specification> get(Long id);
	
	public Optional<Specification> get(Long id, Long brandId);

	public Optional<Specification> getByIdAndModelId(Long id, Long modelId);

	public Optional<Specification> get(Long id, Long brandId, Long modelId);

	public Specification save(Specification id);

	public void delete(Long id);
}
