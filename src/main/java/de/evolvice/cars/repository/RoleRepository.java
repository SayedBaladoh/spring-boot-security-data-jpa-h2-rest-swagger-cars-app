package de.evolvice.cars.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.evolvice.cars.entity.Role;
import de.evolvice.cars.entity.RoleName;
/**
 * Role Repository
 * 
 * @author sayed
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName roleName);
}
