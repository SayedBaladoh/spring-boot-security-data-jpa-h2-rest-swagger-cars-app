package de.evolvice.cars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.evolvice.cars.entity.Brand;
/**
 * Brand Repository
 * 
 * @author sayed
 *
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
