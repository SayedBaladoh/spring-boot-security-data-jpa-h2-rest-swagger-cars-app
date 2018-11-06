package de.evolvice.cars.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import de.evolvice.cars.entity.Brand;

/**
 * Brand Service
 * 
 * @author SayedBaladoh
 *
 */
public interface BrandService {

	public Page<Brand> getAll(Pageable pageable);

	public Optional<Brand> get(Long id);

	public Brand save(Brand brand);

	public void delete(Long id);

}
