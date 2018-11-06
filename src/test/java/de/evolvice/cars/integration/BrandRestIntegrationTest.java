package de.evolvice.cars.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import de.evolvice.cars.CarsApplication;
import de.evolvice.cars.entity.Brand;
import de.evolvice.cars.payload.LoginRequest;
import de.evolvice.cars.repository.BrandRepository;
import de.evolvice.cars.util.JsonUtil;

/**
 * Brand Rest Integration tests
 * 
 * Test the Brand rest web services' integration tests
 * 
 * @author Sayed Baladoh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = CarsApplication.class)
@AutoConfigureMockMvc
public class BrandRestIntegrationTest {

	private final String API_URL = "/api/brands";
	private final Long INVALID_ID = -99L;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private BrandRepository repository;

	@After
	public void cleanUp() {
		// resetDb
		repository.deleteAll();
	}

	/**
	 * Validate get all brands
	 * 
	 * @throws Exception
	 */
	@Test
	// @Ignore
	public void givenBrands_whenGetBrands_thenReturnBrandsWithStatus200()
			throws Exception {

		// Data preparation
		String accessToken = obtainAccessToken("sayedbaladoh", "sayedbaladoh");

		createTestBrand("Jeep");
		createTestBrand("Suzuki");

		// Method call and Verification
		mvc.perform(get(API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(2))));
	}

	/**
	 * Validate findById with valid Id
	 */
	@Test
	public void givenBrands_whenGetBrandByID_thenReturnBrandWithStatus200() throws Exception {
		// Data preparation
		String accessToken = obtainAccessToken("sayedbaladoh", "sayedbaladoh");

		Brand brand = createTestBrand("BMW_Test");

		// Method call and Verification
		mvc.perform(get(API_URL + "/" + brand.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.id").value(brand.getId()))
				.andExpect(jsonPath("$.name").value(brand.getName()));
	}

	/**
	 * Validate findById with invalid Id
	 */
	@Test
	public void givenBrand_whenGetInavlidBrandId_thenReturnNotFound() throws Exception {
		// Data preparation
		String accessToken = obtainAccessToken("sayedbaladoh", "sayedbaladoh");

		// Method call and Verification
		mvc.perform(get(API_URL + "/" + INVALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().is(404));
	}

	/**
	 * Verify post valid Brand with Authorized Token
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	// @Ignore
	public void whenValidInput_thenCreateAndReturnBrand() throws IOException, Exception {
		// Data preparation
		String accessToken = obtainAccessToken("sayedbaladoh", "sayedbaladoh");
		Brand brand = new Brand("Mitsubishi", "www.mitsubishi.com");

		// Method call and Verification
		mvc.perform(post(API_URL)
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(brand)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.website").exists())
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.name", is(brand.getName())))
				.andExpect(jsonPath("$.website", is(brand.getWebsite())));

		List<Brand> found = repository.findAll();
		assertThat(found)
				.extracting(Brand::getName)
				.contains(brand.getName());
	}

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	/**
	 * Verify post invalid Brand with Authorized Token
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	// @Ignore
	public void whenInvalidInput_thenNotCreateed() throws IOException, Exception {
		// Data preparation
		String accessToken = obtainAccessToken("sayedbaladoh", "sayedbaladoh");
		Brand brand = new Brand(null, "www.mitsubishi.com");

		// Method call and Verification
		mvc.perform(post(API_URL)
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(brand)))
				.andExpect(status().is4xxClientError())
				// .andExpect(status().is(400))
				.andExpect(jsonPath("$.id").doesNotExist());
	}

	/**
	 * Verify post valid Brand with UnAuthorized Token
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	// @Ignore
	public void whenValidInputAndNotAuthorized_thenNotCreated() throws IOException, Exception {
		// Data preparation
		Brand brand = new Brand("Mitsubishi", "www.mitsubishi.com");

		// Method call and Verification
		mvc.perform(post(API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(brand)))
				.andExpect(status().is4xxClientError())
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.id").doesNotExist());
	}

	/**
	 * Verify Put valid Brand with Authorized Token
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	// @Ignore
	public void whenValidInput_thenEditAndReturnBrand() throws IOException, Exception {
		// Data preparation
		String accessToken = obtainAccessToken("sayedbaladoh", "sayedbaladoh");
		Brand brand = createTestBrand("Nissan_Test");

		brand.setName("Nissan_test_update");
		brand.setWebsite("www.nissan_test.com");

		// Method call and Verification
		mvc.perform(put(API_URL + "/" + brand.getId())
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(brand)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.website").exists())
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.id").value(brand.getId()))
				.andExpect(jsonPath("$.name", is(brand.getName())))
				.andExpect(jsonPath("$.website", is(brand.getWebsite())));
	}

	/**
	 * Verify Put inValid Brand ID with Authorized Token
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	// @Ignore
	public void whenInValidInput_thenNotEdited() throws IOException, Exception {
		// Data preparation
		String accessToken = obtainAccessToken("sayedbaladoh", "sayedbaladoh");
		Brand brand = createTestBrand("Nissan_Test");

		brand.setName("Nissan_test_update");
		brand.setWebsite("www.nissan_test.com");

		// Method call and Verification
		mvc.perform(put(API_URL + "/" + INVALID_ID)
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(brand)))
				.andExpect(status().is(404));
	}

	/**
	 * Verify Delete valid Brand Id with Authorized Token
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	// @Ignore
	public void whenValidBrandId_thenBrandRemoved() throws IOException, Exception {
		// Data preparation
		String accessToken = obtainAccessToken("sayedbaladoh", "sayedbaladoh");
		Brand brand = createTestBrand("Nissan_Test");

		// Method call and Verification
		mvc.perform(delete(API_URL + "/" + brand.getId())
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(brand)))
				.andExpect(status().isOk());
	}

	/**
	 * Verify Delete inValid Brand ID with Authorized Token
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	// @Ignore
	public void whenInValidBrandId_thenBrandNotDeleted() throws IOException, Exception {
		// Data preparation
		String accessToken = obtainAccessToken("sayedbaladoh", "sayedbaladoh");
		// Method call and Verification
		mvc.perform(put(API_URL + "/" + INVALID_ID)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}

	/**
	 * Get Access Token
	 * 
	 * @param usernameOrEmail
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private String obtainAccessToken(String usernameOrEmail, String password) throws Exception {
		// Data preparation
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsernameOrEmail(usernameOrEmail);
		loginRequest.setPassword(password);
		// Method call and Verification
		ResultActions result = mvc.perform(post("/api/auth/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(loginRequest)))
				.andExpect(status().isOk());

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("accessToken").toString();
	}

	/**
	 * Save Brand
	 * 
	 * @param name
	 * @return
	 */
	private Brand createTestBrand(String name) {
		Brand brand = new Brand(name);
		return repository.saveAndFlush(brand);
	}
}
