package de.evolvice.cars.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.evolvice.cars.entity.Brand;
import de.evolvice.cars.repository.BrandRepository;
import de.evolvice.cars.service.BrandService;

/**
 * Brand Service Implementation
 * 
 * @author SayedBaladoh
 *
 */
@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandRepository brandRepository;

	@Override
	public Page<Brand> getAll(Pageable pageable) {
		Page<Brand> brands = brandRepository.findAll(pageable);
		return brands;
	}

	@Override
	public Optional<Brand> get(Long id) {
		// Optional<Brand> optEmp = brandRepository.findById(id);
		// return optEmp.get();
		return brandRepository.findById(id);
	}

	@Override
	public Brand save(Brand brand) {
		return brandRepository.save(brand);
	}

	@Override
	public void delete(Long id) {
		brandRepository.deleteById(id);
	}

}
