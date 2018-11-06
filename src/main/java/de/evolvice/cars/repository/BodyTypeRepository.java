package de.evolvice.cars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.evolvice.cars.entity.BodyType;
/**
 * Body Type Repository
 * 
 * @author sayed
 *
 */
@Repository
public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {

}
