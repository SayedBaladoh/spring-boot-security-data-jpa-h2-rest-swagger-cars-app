package de.evolvice.cars.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.evolvice.cars.entity.BodyType;
import de.evolvice.cars.repository.BodyTypeRepository;
import de.evolvice.cars.service.BodyTypeService;

/**
 * BodyBodyType Service Implementation
 * 
 * @author SayedBaladoh
 *
 */
@Service
public class BodyTypeServiceImpl implements BodyTypeService {

	@Autowired
	private BodyTypeRepository BodyTypeRepository;

	@Override
	public Page<BodyType> getAll(Pageable pageable) {
		Page<BodyType> bodyBodyTypes = BodyTypeRepository.findAll(pageable);
		return bodyBodyTypes;
	}

	@Override
	public Optional<BodyType> get(Long id) {
		return BodyTypeRepository.findById(id);
	}

	@Override
	public BodyType save(BodyType bodyBodyType) {
		return BodyTypeRepository.save(bodyBodyType);
	}

	@Override
	public void delete(Long id) {
		BodyTypeRepository.deleteById(id);
	}

}
