package de.evolvice.cars.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.evolvice.cars.entity.Brand;
import de.evolvice.cars.exception.BadRequestException;
import de.evolvice.cars.exception.ResourceNotFoundException;
import de.evolvice.cars.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Brand Rest Controller
 * 
 * @author SayedBaladoh
 *
 */
@RestController
@RequestMapping("/api/brands")
@Api(
		value = "Brand",
		description = "Operations for cars brands")
public class BrandController {

	@Autowired
	private BrandService brandService;

	@GetMapping()
	@ApiOperation(
			value = "View a page of list of available brands",
			response = Page.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Page<Brand> getBrands(Pageable pageable) {
		return brandService.getAll(pageable);
	}

	@GetMapping("/{brandId}")
	@ApiOperation(
			value = "Search a brand with an ID",
			response = Brand.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Brand getBrand(@PathVariable(
			name = "brandId") Long brandId) {
		return brandService.get(brandId).orElseThrow(() -> new ResourceNotFoundException("Brand", "Id", brandId));
	}

	@PostMapping()
	@ApiOperation(
			value = "Add a brand",
			response = Brand.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public ResponseEntity<Brand> addBrand(@Valid @RequestBody Brand brand) {
		try {
			HttpStatus status = HttpStatus.CREATED;
			Brand saved = brandService.save(brand);
			return new ResponseEntity<>(saved, status);
		} catch (DataIntegrityViolationException ex) {
			throw new BadRequestException("Sorry! You have already brand with the same id or name.");
		}
	}

	@PutMapping("/{brandId}")
	@ApiOperation(
			value = "Edit a brand",
			response = Brand.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Brand updateBrand(@PathVariable(
			name = "brandId") Long brandId, @RequestBody Brand brandRequest) {
		return brandService.get(brandId).map(brand -> {
			if (brandRequest.getName() != null) {
				if (brandRequest.getName().equals(""))
					throw new BadRequestException("Sorry! The name must not be blank");
				else
					brand.setName(brandRequest.getName());
			}
			if (brandRequest.getWebsite() != null)
				brand.setWebsite(brandRequest.getWebsite());
			try {
				return brandService.save(brand);
			} catch (DataIntegrityViolationException ex) {
				throw new BadRequestException("Sorry! You have already brand with the same name.");
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Brand", "Id", brandId));
	}

	@DeleteMapping("/{brandId}")
	@ApiOperation(
			value = "Remove a brand")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public ResponseEntity<?> deleteBrand(@PathVariable(
			name = "brandId") Long brandId) {
		return brandService.get(brandId).map(brand -> {
			brandService.delete(brandId);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Brand", "Id", brandId));
	}
}
