package de.evolvice.cars.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import de.evolvice.cars.entity.BodyType;

/**
 * Body Type Service
 * 
 * @author SayedBaladoh
 *
 */
public interface BodyTypeService {

	public Page<BodyType> getAll(Pageable pageable);

	public Optional<BodyType> get(Long id);

	public BodyType save(BodyType bodyType);

	public void delete(Long id);

}
