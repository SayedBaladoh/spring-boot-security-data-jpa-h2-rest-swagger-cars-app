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
import org.springframework.web.bind.annotation.RestController;

import de.evolvice.cars.entity.Model;
import de.evolvice.cars.exception.BadRequestException;
import de.evolvice.cars.exception.ResourceNotFoundException;
import de.evolvice.cars.service.ModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Model Rest Controller
 * 
 * @author SayedBaladoh
 *
 */
@RestController
@RequestMapping("/api/brands")
@Api(
		value = "BodyType",
		description = "Operations for cars models")
public class ModelController {

	@Autowired
	private ModelService modelService;

	@GetMapping("/models")
	@ApiOperation(
			value = "View a page of list of available car models",
			response = Page.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Page<Model> getModels(Pageable pageable) {
		return modelService.getAll(pageable);
	}

	@GetMapping("/{brandId}/models")
	@ApiOperation(
			value = "Search a model by brandId",
			response = Page.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Page<Model> getModels(@PathVariable(
			name = "brandId") Long brandId, Pageable pageable) {
		return modelService.getAll(brandId, pageable);
	}

	@GetMapping("/models/{modelId}")
	@ApiOperation(
			value = "Search a model by modelId",
			response = Model.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Model getModel(@PathVariable(
			name = "modelId") Long modelId) {
		return modelService.get(modelId).orElseThrow(() -> new ResourceNotFoundException("Model", "Id", modelId));
	}

	@GetMapping("/{brandId}/models/{modelId}")
	@ApiOperation(
			value = "Search a model by modelId and brandId",
			response = Model.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Model getModel(@PathVariable(
			name = "brandId") Long brandId,
			@PathVariable(
					name = "modelId") Long modelId) {
		return modelService.get(modelId, brandId).orElseThrow(() -> new ResourceNotFoundException(
				"Model is not found with Id: " + modelId + " and prand Id: " + brandId));
	}

	@PostMapping("/{brandId}/models")
	@ApiOperation(
			value = "Add a model",
			response = Model.class)
	// @PreAuthorize("hasRole('USER')")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Model addModel(@PathVariable(
			name = "brandId") Long brandId, @Valid @RequestBody Model model) {
		try {
			return modelService.save(model);
		} catch (DataIntegrityViolationException ex) {
			throw new BadRequestException("Sorry! You have already model with the same id or name");
		}
	}

	@PutMapping("/models/{modelId}")
	@ApiOperation(
			value = "Edit a model by modelId",
			response = Model.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Model updateModel(@PathVariable(
			name = "modelId") Long modelId, @RequestBody Model modelRequest) {
		return modelService.get(modelId).map(model -> {
			if (modelRequest.getName() != null) {
				if (modelRequest.getName().equals(""))
					throw new BadRequestException("Sorry! The name must not be blank");
				else
					model.setName(modelRequest.getName());
			}
			if (modelRequest.getBrand() != null && modelRequest.getBrand().getId() != model.getBrand().getId())
				model.setBrand(modelRequest.getBrand());
			if (modelRequest.getYearFrom() != 0 && modelRequest.getYearFrom() != model.getYearFrom())
				model.setYearFrom(modelRequest.getYearFrom());
			if (modelRequest.getYearTo() != 0 && modelRequest.getYearTo() != model.getYearTo())
				model.setYearTo(modelRequest.getYearTo());

			try {
				return modelService.save(model);
			} catch (DataIntegrityViolationException ex) {
				throw new BadRequestException("Sorry! You have already model with the same name");
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Model", "Id", modelId));
	}

	@PutMapping("/{brandId}/models/{modelId}")
	@ApiOperation(
			value = "Edit a model by modelId and brandId",
			response = Model.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public Model updateModel(@PathVariable(
			name = "brandId") Long brandId,
			@PathVariable(
					name = "modelId") Long modelId,
			@RequestBody Model modelRequest) {
		return modelService.get(modelId, brandId).map(model -> {
			if (modelRequest.getName() != null) {
				if (modelRequest.getName().equals(""))
					throw new BadRequestException("Sorry! The name must not be blank");
				else
					model.setName(modelRequest.getName());
			}
			if (modelRequest.getBrand() != null && modelRequest.getBrand().getId() != model.getBrand().getId())
				model.setBrand(modelRequest.getBrand());
			if (modelRequest.getYearFrom() != 0 && modelRequest.getYearFrom() != model.getYearFrom())
				model.setYearFrom(modelRequest.getYearFrom());
			if (modelRequest.getYearTo() != 0 && modelRequest.getYearTo() != model.getYearTo())
				model.setYearTo(modelRequest.getYearTo());

			try {
				return modelService.save(model);
			} catch (DataIntegrityViolationException ex) {
				throw new BadRequestException("Sorry! You have already model with the same name");
			}
		}).orElseThrow(() -> new ResourceNotFoundException(
				"Model is not found with Id: " + modelId + " and prand Id: " + brandId));
	}

	@DeleteMapping("/models/{modelId}")
	@ApiOperation(
			value = "Delete a model by modelId")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public ResponseEntity<?> deleteModel(@PathVariable(
			name = "modelId") Long modelId) {
		return modelService.get(modelId).map(model -> {
			modelService.delete(modelId);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Model", "Id", modelId));
	}

	@DeleteMapping("/{brandId}/models/{modelId}")
	@ApiOperation(
			value = "Delete a model by modelId and brandId")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public ResponseEntity<?> deleteModel(@PathVariable(
			name = "brandId") Long brandId,
			@PathVariable(
					name = "modelId") Long modelId) {
		return modelService.get(modelId, brandId).map(model -> {
			modelService.delete(modelId);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException(
				"Model is not found with Id: " + modelId + " and prand Id: " + brandId));
	}
}
