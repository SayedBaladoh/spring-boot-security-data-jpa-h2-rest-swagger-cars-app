package de.evolvice.cars.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import de.evolvice.cars.CarsApplication;
import de.evolvice.cars.entity.Brand;

/**
 * Brand repository unit tests
 * 
 * Test the Brand Repository
 * 
 * @author Sayed Baladoh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = CarsApplication.class)
public class BrandRepositoryTest {

	private final String BRAND_NAME = "Nissan";
	private final String BRAND_WEBSITE = "www.nessan.com";
	private final Long INVALID_ID = -99L;

	@Autowired
	private BrandRepository brandRepository;

	/**
	 * Validate findAll
	 */
	// @Ignore
	@Test
	public void givenSetOfBrands_whenFindAll_thenReturnPageofAllBrands() {
		// Data preparation
		Brand nissan = new Brand(BRAND_NAME);
		Brand suzuki = new Brand("Suzuki");
		Brand jeep = new Brand("Jeep");

		brandRepository.save(nissan);
		brandRepository.save(suzuki);
		brandRepository.save(jeep);

		// Method call
		Page<Brand> allBrands = brandRepository.findAll(PageRequest.of(0, 3));

		// Verification
		assertThat(allBrands.getContent())
				.hasSize(3)
				.extracting(Brand::getName)
				.contains(nissan.getName(), suzuki.getName(), jeep.getName());

	}

	/**
	 * Validate findById with valid Id
	 */
	// @Ignore
	@Test
	public void givenBrandInDB_WhenFindById_ThenReturnOptionalWithBrand() {
		// Data preparation
		Brand brand = new Brand();
		brand.setName(BRAND_NAME);
		brand.setWebsite(BRAND_WEBSITE);
		brandRepository.save(brand);

		// Method call
		Optional<Brand> foundBrand = brandRepository.findById(brand.getId());

		// Verification
		assertThat(foundBrand.isPresent())
				.isEqualTo(true);
		assertThat(foundBrand.get().getName())
				.isEqualTo(brand.getName());
	}

	/**
	 * Validate findById with invalid Id
	 */
	// @Ignore
	@Test
	public void givenEmptyDB_WhenFindById_ThenReturnEmptyOptional() {
		// Method call
		Optional<Brand> foundBrand = brandRepository.findById(INVALID_ID);

		// Verification
		assertThat(foundBrand.isPresent()).isEqualTo(false);
	}

	/**
	 * Validate save brand with valid brand
	 */
	// @Ignore
	@Test
	public void whenValidBrand_thenBrandShouldBeSavedAndReturned() {
		// Data preparation
		Brand brand = new Brand(BRAND_NAME, BRAND_WEBSITE);
		brandRepository.save(brand);

		// Method call
		Brand savedBrand = brandRepository.save(brand);

		// Verification
		assertThat(savedBrand)
				.isNotNull()
				.extracting(Brand::getId)
				.isNotNull()
				.isNotEmpty();
		assertEquals(brand.getName(), savedBrand.getName());
		assertEquals(brand.getWebsite(), savedBrand.getWebsite());
	}

	/**
	 * Validate save brand with invalid brand
	 */
	// @Ignore
	@Test(
			expected = InvalidDataAccessApiUsageException.class)
	public void whenInvalidBrand_thenBrandShouldNotBeSaved() {
		// Method call
		Brand savedBrand = brandRepository.save(null);

		// Verification
		assertThat(savedBrand)
				.isNull();
	}

	/**
	 * Validate save brand with invalid brand name that is existed
	 */
	// @Ignore
	@Test(
			expected = DataIntegrityViolationException.class)
	public void whenExistingBrandName_thenBrandShouldNotBeSaved() {
		// Data preparation
		Brand brand1 = new Brand(BRAND_NAME, BRAND_WEBSITE);
		Brand brand2 = new Brand(BRAND_NAME, BRAND_WEBSITE);
		// Method call
		brandRepository.save(brand1);

		// Method call Again for Verification
		brandRepository.save(brand2);
	}

	/**
	 * Validate edit brand with valid brand
	 */
	// @Ignore
	@Test
	public void whenValidBrand_thenBrandShouldBeUpdatedAndReturned() {
		// Data preparation
		Brand brand = new Brand(BRAND_NAME, BRAND_WEBSITE);

		// Method call
		Brand savedBrand = brandRepository.save(brand);

		// Update Object
		savedBrand.setName("New Nissan");

		// Method call
		Brand updatedBrand = brandRepository.save(savedBrand);

		// Verification
		assertThat(updatedBrand)
				.isNotNull()
				.extracting(Brand::getId).isNotNull().isNotEmpty();
		assertEquals(updatedBrand.getId(), savedBrand.getId());
		assertEquals(updatedBrand.getName(), savedBrand.getName());
		assertEquals(updatedBrand.getWebsite(), savedBrand.getWebsite());
	}

	/**
	 * Validate update brand with invalid brand name that is existed
	 */
	// @Ignore
	@Test(
			expected = DataIntegrityViolationException.class)
	public void whenExistingBrandName_thenBrandShouldNotBeUpdated() {
		// Data preparation
		Brand nissan = new Brand(BRAND_NAME);
		Brand suzuki = new Brand("Suzuki");

		nissan = brandRepository.save(nissan);
		suzuki = brandRepository.save(suzuki);

		suzuki.setName(BRAND_NAME);

		// Method call
		brandRepository.save(suzuki);
	}

	/**
	 * Validate delete brand with valid brand Id
	 */
	@Test
	public void whenValidBrandId_thenBrandShouldBeRemoved() {
		// Data preparation
		Brand nissan = new Brand(BRAND_NAME);

		nissan = brandRepository.save(nissan);

		// Method call
		brandRepository.deleteById(nissan.getId());
	}

	/**
	 * Validate delete brand with invalid brand Id
	 */
	@Test(
			expected = EmptyResultDataAccessException.class)
	public void whenInvalidBrandId_thenBrandShouldNotBeRemoved() {
		// Method call
		brandRepository.deleteById(INVALID_ID);
	}

	@After
	// @AfterThrowing
	public void cleanUp() {
		brandRepository.deleteAll();
	}

}
