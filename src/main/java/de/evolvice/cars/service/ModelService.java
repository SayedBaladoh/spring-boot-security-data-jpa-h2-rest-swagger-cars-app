package de.evolvice.cars.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import de.evolvice.cars.entity.Model;

/**
 * Model Service
 * 
 * @author SayedBaladoh
 *
 */
public interface ModelService {

	public Page<Model> getAll(Pageable pageable);
	
	public Page<Model> getAll(Long brandId, Pageable pageable);

	public Optional<Model> get(Long id);
	
	public Optional<Model> get(Long id, Long brandId);

	public Model save(Model model);

	public void delete(Long id);

	

}
