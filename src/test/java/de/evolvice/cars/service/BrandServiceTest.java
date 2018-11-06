package de.evolvice.cars.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import de.evolvice.cars.entity.Brand;
import de.evolvice.cars.repository.BrandRepository;
import de.evolvice.cars.service.impl.BrandServiceImpl;

/**
 * Brand service unit tests
 * 
 * Test the Brand Service Implementation: test the service logic
 * 
 * @author Sayed Baladoh
 *
 */
@RunWith(SpringRunner.class)
public class BrandServiceTest {

	private static final Long BRAND_ONE_ID = 11L;
	private static final Long INVALID_ID = -99L;
	private static final String BRAND_ONE_NAME = "Honda";

	@Autowired
	private BrandService brandService;

	@MockBean
	private BrandRepository repoMock;

	@TestConfiguration
	static class BrandServiceImplTestContextConfiguration {
		@Bean
		public BrandService brandService() {
			return new BrandServiceImpl();
		}
	}

	@Before
	public void setUp() {
		// Data preparation
		Brand honda = new Brand(BRAND_ONE_NAME);
		honda.setId(BRAND_ONE_ID);

		Brand datsun = new Brand("Datsun");
		Brand isuzu = new Brand("Isuzu");

		Brand savedBrand = new Brand(5l, "BMW", "www.bmw.com");

		List<Brand> brands = Arrays.asList(honda, datsun, isuzu);
		Page<Brand> pageBrands = new PageImpl<Brand>(brands);

		Mockito.when(repoMock.findById(BRAND_ONE_ID)).thenReturn(Optional.of(honda));
		Mockito.when(repoMock.findAll(any(Pageable.class))).thenReturn(pageBrands);
		Mockito.when(repoMock.findById(INVALID_ID).orElse(null)).thenReturn(null);
		Mockito.when(repoMock.save(any(Brand.class))).thenReturn(savedBrand);
	}

	/**
	 * Validate get all brands
	 */
	@Test
	public void given3Brands_whengetAll_thenReturn3Records() {
		// Data preparation
		Brand honda = new Brand(BRAND_ONE_NAME);
		Brand datsun = new Brand("Datsun");
		Brand isuzu = new Brand("Isuzu");

		// Method call
		Page<Brand> allBrands = brandService.getAll(PageRequest.of(0, 5));

		// Verification
		assertThat(allBrands.getContent())
				.hasSize(3)
				.extracting(Brand::getName)
				.contains(honda.getName(), datsun.getName(), isuzu.getName());

		Mockito.verify(repoMock, Mockito.times(1)).findAll(PageRequest.of(0, 5));
		Mockito.verifyNoMoreInteractions(repoMock);
		Mockito.reset(repoMock);
	}

	/**
	 * Validate get brand by Id, findById and brand exists
	 */
	// @Ignore
	@Test
	public void whenValidId_thenBrandShouldBeFound() {
		// Method call
		Optional<Brand> brand = brandService.get(BRAND_ONE_ID);

		// Verification
		verifyFindByIdIsCalledOnce();
		assertThat(brand)
				.isNotNull()
				.isNotEmpty();
		assertThat(brand.get().getName())
				.isEqualTo(BRAND_ONE_NAME);
	}

	/**
	 * Validate get brand by Id using invalid Id, findById and brand IsNull
	 */
	// @Ignore
	@Test
	public void whenInValidId_thenBrandShouldNotBeFound() {
		// Method call
		Optional<Brand> brand = brandService.get(INVALID_ID);

		// Verification
		verifyFindByIdIsCalledOnce();
		assertThat(brand).isNull();
	}

	/**
	 * Validate save brand with valid brand
	 */
	@Test
	public void whenValidBrand_thenBrandShouldBeSavedAndReturned() {
		// Data preparation
		Brand brand = new Brand("BMW", "www.bmw.com");

		// Method call
		Brand savedBrand = brandService.save(brand);

		// Verification
		assertThat(savedBrand)
				.isNotNull()
				.extracting(Brand::getId).isNotNull().isNotEmpty();
		assertEquals(brand.getName(), savedBrand.getName());
		assertEquals(brand.getWebsite(), savedBrand.getWebsite());

		Mockito.verify(repoMock, Mockito.times(1)).save(any(Brand.class));
		Mockito.verifyNoMoreInteractions(repoMock);
	}

	/**
	 * Validate save brand with invalid brand
	 */
	@Test
	public void whenInvalidBrand_thenBrandShouldNotBeSaved() {
		// Method call
		Brand savedBrand = brandService.save(null);
		// Verification
		assertThat(savedBrand)
				.isNull();
	}

	/**
	 * Validate save brand with invalid brand has existing name
	 */
	@Test(
			expected = DataIntegrityViolationException.class)
	public void whenExistingBrandName_thenBrandShouldNotBeSaved() {
		// Method call
		Brand isuzu = new Brand("Isuzu");
		// Verification
		doThrow(new DataIntegrityViolationException("")).when(repoMock).save(isuzu);
		brandService.save(isuzu);
	}

	/**
	 * Validate edit brand with valid brand
	 */
	@Test
	public void whenValidBrand_thenBrandShouldBeUpdatedAndReturned() {
		// Data preparation
		Brand brand = new Brand(5l, "BMW", "www.bmw.com");

		// Method call
		Brand savedBrand = brandService.save(brand);

		// Verification
		assertThat(savedBrand)
				.isNotNull()
				.extracting(Brand::getId).isNotNull().isNotEmpty();
		assertEquals(brand.getName(), savedBrand.getName());
		assertEquals(brand.getWebsite(), savedBrand.getWebsite());

		Mockito.verify(repoMock, Mockito.times(1)).save(any(Brand.class));
		Mockito.verifyNoMoreInteractions(repoMock);
	}

	/**
	 * Validate delete brand with valid brand Id
	 */
	@Test
	public void whenValidBrandId_thenBrandShouldBeRemoved() {
		// Method call
		brandService.delete(BRAND_ONE_ID);

		// Verification
		Mockito.verify(repoMock, Mockito.times(1)).deleteById(BRAND_ONE_ID);
		Mockito.verifyNoMoreInteractions(repoMock);
	}

	/**
	 * Verify FindById is called once
	 */
	private void verifyFindByIdIsCalledOnce() {
		Mockito.verify(repoMock, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(repoMock);
		Mockito.reset(repoMock);
	}

}
