package de.evolvice.cars.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.evolvice.cars.entity.Model;
import de.evolvice.cars.repository.ModelRepository;
import de.evolvice.cars.service.ModelService;

/**
 * Model Service Implementation
 * 
 * @author SayedBaladoh
 *
 */
@Service
public class ModelServiceImpl implements ModelService {

	@Autowired
	private ModelRepository modelRepository;

	@Override
	public Page<Model> getAll(Pageable pageable) {
		Page<Model> models = modelRepository.findAll(pageable);
		return models;
	}
	
	@Override
	public Page<Model> getAll(Long brandId, Pageable pageable) {
		Page<Model> models = modelRepository.findByBrandId(brandId, pageable);
		return models;
	}

	@Override
	public Optional<Model> get(Long id) {
		return modelRepository.findById(id);
	}

	@Override
	public Optional<Model> get(Long id, Long brandId) {
		return modelRepository.findByIdAndBrandId(id, brandId);
	}
	
	@Override
	public Model save(Model model) {
		return modelRepository.save(model);
	}

	@Override
	public void delete(Long id) {
		modelRepository.deleteById(id);
	}

}
