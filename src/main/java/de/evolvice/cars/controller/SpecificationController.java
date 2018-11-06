package de.evolvice.cars.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.evolvice.cars.entity.Specification;
import de.evolvice.cars.exception.BadRequestException;
import de.evolvice.cars.exception.ResourceNotFoundException;
import de.evolvice.cars.service.SpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Car Specification Rest Controller
 * 
 * @author SayedBaladoh
 *
 */
@RestController
@RequestMapping("/api/brands")
@Api(
		value = "BodyType",
		description = "Operations for car specifications")
public class SpecificationController {

	@Autowired
	private SpecificationService specificationService;

	@GetMapping("/models/specifications")
	@ApiOperation(
			value = "View a page of list of available car specifications",
			response = Page.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Page<Specification> getSpecifications(Pageable pageable) {
		return specificationService.getAll(pageable);
	}

	@GetMapping("/{brandId}/models/specifications")
	@ApiOperation(
			value = "View a page of list of available car specifications filtered by brandId",
			response = Page.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Page<Specification> getSpecifications(@PathVariable(
			name = "brandId") Long brandId, Pageable pageable) {
		return specificationService.getAll(brandId, pageable);
	}

	@GetMapping("/models/{modelId}/specifications")
	@ApiOperation(
			value = "View a page of list of available car specifications filtered by modelId",
			response = Page.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Page<Specification> getSpecificationsByModel(@PathVariable(
			name = "modelId") Long modelId, Pageable pageable) {
		return specificationService.getAll(pageable, modelId);
	}

	@GetMapping("/{brandId}/models/{modelId}/specifications")
	@ApiOperation(
			value = "View a page of list of available car specifications filtered by brandId and modelId",
			response = Page.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Page<Specification> getSpecifications(@PathVariable(
			name = "brandId") Long brandId,
			@PathVariable(
					name = "modelId") Long modelId,
			Pageable pageable) {
		return specificationService.getAll(brandId, modelId, pageable);
	}

	@GetMapping(
			value = "/models/specifications",
			params = { "bodyTypeId" })
	@ApiOperation(
			value = "View a page of list of available car specifications filtered by bodyTypeId",
			response = Page.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Page<Specification> getSpecificationsByBodyType(@RequestParam(
			name = "bodyTypeId") Long bodyTypeId, Pageable pageable) {
		return specificationService.getAllByBodyTypeId(bodyTypeId, pageable);
	}

	@GetMapping("/models/specifications/{specificationId}")
	@ApiOperation(
			value = "Search a Specification by specificationId",
			response = Specification.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Specification getSpecification(
			@PathVariable(
					name = "specificationId") Long specificationId) {
		return specificationService.get(specificationId)
				.orElseThrow(() -> new ResourceNotFoundException("Specification", "Id", specificationId));
	}

	@GetMapping("/{brandId}/models/specifications/{specificationId}")
	@ApiOperation(
			value = "Search a Specification by specificationId and brandId",
			response = Specification.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Specification getSpecification(@PathVariable(
			name = "brandId") Long brandId,
			@PathVariable(
					name = "specificationId") Long specificationId) {
		return specificationService.get(specificationId, brandId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Specification is not found with Id: " + specificationId + " and prand Id: " + brandId));
	}

	@GetMapping("/models/{modelId}/specifications/{specificationId}")
	@ApiOperation(
			value = "Search a Specification by specificationId and modelId",
			response = Specification.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Specification getSpecificationByModel(@PathVariable(
			name = "modelId") Long modelId,
			@PathVariable(
					name = "specificationId") Long specificationId) {
		return specificationService.getByIdAndModelId(specificationId, modelId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Specification is not found with Id: " + specificationId + " and model Id: " + modelId));
	}

	@GetMapping("/{brandId}/models/{modelId}/specifications/{specificationId}")
	@ApiOperation(
			value = "Search a Specification by specificationId, modelId and brandId",
			response = Specification.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Specification getSpecification(@PathVariable(
			name = "brandId") Long brandId,
			@PathVariable(
					name = "modelId") Long modelId,
			@PathVariable(
					name = "specificationId") Long specificationId) {
		return specificationService.get(specificationId, brandId, modelId)
				.orElseThrow(() -> new ResourceNotFoundException("Specification is not found with Id: "
						+ specificationId + ", prand Id: " + brandId + " and model Id: " + modelId));
	}

	@PostMapping("/models/specifications")
	@ApiOperation(
			value = "Add a car specifications",
			response = Specification.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Specification addSpecification(@Valid @RequestBody Specification specification) {
		try {
			return specificationService.save(specification);
		} catch (DataIntegrityViolationException ex) {
			throw new BadRequestException("Sorry! You have already specification with the same id or name.");
		}
	}

	@PutMapping("/models/specifications/{specificationId}")
	@ApiOperation(
			value = "Edit a car specifications",
			response = Specification.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Specification updateSpecification(
			@PathVariable(
					name = "specificationId") Long specificationId,
			@RequestBody Specification specificationRequest) {
		return specificationService.get(specificationId).map(specification -> {

			if (specificationRequest.getBodyType() != null
					&& specificationRequest.getBodyType().getId() != specification.getBodyType().getId())
				specification.setBodyType(specificationRequest.getBodyType());

			if (specificationRequest.getModel() != null
					&& specificationRequest.getModel().getId() != specification.getModel().getId())
				specification.setModel(specificationRequest.getModel());

			if (specificationRequest.getDoorsNo() != 0
					&& specificationRequest.getDoorsNo() != specification.getDoorsNo())
				specification.setDoorsNo(specificationRequest.getDoorsNo());

			if (specificationRequest.getSeatsNo() != 0
					&& specificationRequest.getSeatsNo() != specification.getSeatsNo())
				specification.setSeatsNo(specificationRequest.getSeatsNo());

			if (specificationRequest.getHeight() != 0
					&& specificationRequest.getHeight() != specification.getHeight())
				specification.setHeight(specificationRequest.getHeight());

			if (specificationRequest.getWidth() != 0
					&& specificationRequest.getWidth() != specification.getWidth())
				specification.setWidth(specificationRequest.getWidth());

			if (specificationRequest.getLength() != 0
					&& specificationRequest.getLength() != specification.getLength())
				specification.setLength(specificationRequest.getLength());

			try {
				return specificationService.save(specification);
			} catch (DataIntegrityViolationException ex) {
				throw new BadRequestException("Sorry! You have already specification with the same name.");
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Specification", "Id", specificationId));
	}

	@DeleteMapping("/models/specifications/{specificationId}")
	@ApiOperation(
			value = "remove a car specifications")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public ResponseEntity<?> deleteSpecification(
			@PathVariable(
					name = "specificationId") Long specificationId) {
		return specificationService.get(specificationId).map(specification -> {
			specificationService.delete(specificationId);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Specification", "Id", specificationId));
	}
}
