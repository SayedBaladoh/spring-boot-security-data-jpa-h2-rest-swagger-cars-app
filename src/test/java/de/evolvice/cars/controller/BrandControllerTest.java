package de.evolvice.cars.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import de.evolvice.cars.entity.Brand;
import de.evolvice.cars.service.BrandService;
import de.evolvice.cars.util.JsonUtil;

/**
 * Brand controller unit tests
 * 
 * Test the Brand rest web services' unit tests
 * 
 * @author Sayed Baladoh
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(
		value = BrandController.class,
		secure = false)
@EnableSpringDataWebSupport
public class BrandControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BrandService service;

	/**
	 * Validate get all brands
	 * 
	 * @throws Exception
	 */
	@Test
	public void givenBrandsList_whenGetBrands_thenReturnJsonArray() throws Exception {
		// Data preparation
		Brand nissan = new Brand("Nissan");
		Brand toyota = new Brand("Toyota");
		Brand bmw = new Brand("BMW");

		List<Brand> brands = Arrays.asList(nissan, toyota, bmw);
		Page<Brand> page = new PageImpl<Brand>(brands);

		given(service.getAll(any(Pageable.class)))
				.willReturn(page);

		// Verification
		mvc.perform(get("/api/brands")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(3)))
				.andExpect(jsonPath("$.content[0].name", is(nissan.getName())))
				.andExpect(jsonPath("$.content[1].name", is(toyota.getName())))
				.andExpect(jsonPath("$.content[2].name", is(bmw.getName())));

		verify(service, VerificationModeFactory.times(1)).getAll(any(Pageable.class));
		reset(service);
	}

	/**
	 * Validate get all brands 2nd way
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAll_WhenBrandsExist_ShouldReturnBrandsPage() throws Exception {
		// Data preparation
		Brand brand = new Brand("Nissan");

		List<Brand> brands = Arrays.asList(brand);
		Page<Brand> page = new PageImpl<>(brands, new PageRequest(0, 10), brands.size());

		when(service.getAll(any(PageRequest.class)))
				.thenReturn(page);

		// Verification
		mvc.perform(get("/api/brands")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	/**
	 * Verify valid Brand Id to get
	 * 
	 * @throws Exception
	 */
	@Test
	public void givenBrand_whenGetBrand_thenReturnBrand() throws Exception {
		// Data preparation
		Brand brand = new Brand(1l, "Nissan", "");

		given(service.get(brand.getId()))
				.willReturn(Optional.of(brand));

		// Verification
		this.mvc.perform(get("/api/brands/" + brand.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.website").exists())
				.andExpect(jsonPath("$.id").value(brand.getId()))
				.andExpect(jsonPath("$.name").value(brand.getName()))
				.andExpect(jsonPath("$.website").value(brand.getWebsite()))
				.andDo(print());

		verify(service, VerificationModeFactory.times(1)).get(brand.getId());
		reset(service);
	}

	/**
	 * Verify invalid Brand Id to get
	 * 
	 * @throws Exception
	 */
	@Test
	public void givenBrand_whenGetInavlidBrandId_thenReturn404() throws Exception {
		// Data preparation
		Brand brand = new Brand(1l, "Nissan", "");

		given(service.get(brand.getId()))
				.willReturn(Optional.of(brand));

		// Verification
		this.mvc.perform(get("/api/brands/55")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(404))
				.andDo(print());

		reset(service);
	}

	/**
	 * Verify post valid Brand
	 * 
	 * @throws Exception
	 */
	@Test
	public void whenPostValidBrand_thenCreateBrand() throws Exception {
		// Data preparation
		Brand brand = new Brand("Nissan", "");

		given(service.save(Mockito.anyObject()))
				.willReturn(brand);

		// Verification
		mvc.perform(post("/api/brands")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(brand)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.website").exists())
				.andExpect(jsonPath("$.name", is("Nissan")));

		verify(service, VerificationModeFactory.times(1)).save(Mockito.anyObject());
		reset(service);
	}

	/**
	 * Verify valid Brand to update
	 * 
	 * @throws Exception
	 */
	@Test
	public void whenPutValidBrand_thenEditBrand() throws Exception {
		// Data preparation
		Brand brand = new Brand(1l, "Nissan", "www.nissan.com");

		given(service.get(brand.getId()))
				.willReturn(Optional.of(brand));

		given(service.save(Mockito.anyObject()))
				.willReturn(brand);

		// Verification
		mvc.perform(put("/api/brands/" + brand.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(brand)))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.website").exists())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name", is("Nissan")))
				.andExpect(jsonPath("$.website").value(brand.getWebsite()))
				.andDo(print());

		verify(service, VerificationModeFactory.times(1)).save(Mockito.anyObject());
		reset(service);
	}

	/**
	 * Verify valid Brand Id to update
	 * 
	 * @throws Exception
	 */
	@Test
	public void whenPutInvalidBrandId_thenNotEditBrand() throws Exception {
		// Data preparation
		Brand brand = new Brand(1l, "Nissan", "www.nissan.com");

		given(service.get(brand.getId()))
				.willReturn(Optional.of(brand));

		given(service.save(Mockito.anyObject()))
				.willReturn(brand);

		// Verification
		mvc.perform(put("/api/brands/55")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(brand)))
				.andExpect(status().is(404))
				.andDo(print());

		reset(service);
	}

	/**
	 * Verify valid Brand Id to delete
	 * 
	 * @throws Exception
	 */

	@Test
	public void whenDeleteValidBrand_thenRemoveBrand() throws Exception {
		// Data preparation
		Brand brand = new Brand(1l, "Nissan", "");

		given(service.get(brand.getId()))
				.willReturn(Optional.of(brand));

		// Verification
		mvc.perform(delete("/api/brands/" + brand.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andDo(print());

		verify(service, VerificationModeFactory.times(1)).delete(brand.getId());
		reset(service);
	}

	/**
	 * Verify invalid Brand Id to delete
	 * 
	 * @throws Exception
	 */
	@Test
	public void whenDeleteInvalidBrandId_thenNotRemoveBrand() throws Exception {
		// Data preparation
		Brand brand = new Brand(1l, "Nissan", "");

		given(service.get(brand.getId()))
				.willReturn(Optional.of(brand));

		// Verification
		mvc.perform(delete("/api/brands/50")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(404))
				.andDo(print());

		reset(service);
	}

}
