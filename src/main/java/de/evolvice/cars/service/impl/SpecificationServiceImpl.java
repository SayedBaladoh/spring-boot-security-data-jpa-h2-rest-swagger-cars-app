package de.evolvice.cars.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.evolvice.cars.entity.Specification;
import de.evolvice.cars.repository.SpecificationRepository;
import de.evolvice.cars.service.SpecificationService;

/**
 * Car Specification Service Implementation
 * 
 * @author SayedBaladoh
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private SpecificationRepository specificationRepository;

	@Override
	public Page<Specification> getAll(Pageable pageable) {
		Page<Specification> specifications = specificationRepository.findAll(pageable);
		return specifications;
	}

	@Override
	public Page<Specification> getAll(Long brandId, Pageable pageable) {
		Page<Specification> specifications = specificationRepository.findByModelBrandId(brandId, pageable);
		return specifications;
	}

	@Override
	public Page<Specification> getAll(Pageable pageable, Long modelId) {
		Page<Specification> specifications = specificationRepository.findByModelId(modelId, pageable);
		return specifications;
	}

	@Override
	public Page<Specification> getAll(Long brandId, Long modelId, Pageable pageable) {
		Page<Specification> specifications = specificationRepository.findByModelBrandIdAndModelId(brandId, modelId,
				pageable);
		return specifications;
	}

	@Override
	public Page<Specification> getAllByBodyTypeId(Long bodyTypeId, Pageable pageable) {
		Page<Specification> specifications = specificationRepository.findByBodyTypeId(bodyTypeId, pageable);
		return specifications;
	}

	@Override
	public Optional<Specification> get(Long id) {
		return specificationRepository.findById(id);
	}

	@Override
	public Optional<Specification> get(Long id, Long brandId) {
		return specificationRepository.findByIdAndModelBrandId(id, brandId);
	}

	@Override
	public Optional<Specification> getByIdAndModelId(Long id, Long modelId) {
		return specificationRepository.findByIdAndModelId(id, modelId);
	}

	@Override
	public Optional<Specification> get(Long id, Long brandId, Long modelId) {
		return specificationRepository.findByIdAndModelBrandIdAndModelId(id, brandId, modelId);
	}

	@Override
	public Specification save(Specification specification) {
		return specificationRepository.save(specification);
	}

	@Override
	public void delete(Long id) {
		specificationRepository.deleteById(id);
	}

}
