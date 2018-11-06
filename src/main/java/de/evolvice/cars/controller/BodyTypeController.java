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

import de.evolvice.cars.entity.BodyType;
import de.evolvice.cars.exception.BadRequestException;
import de.evolvice.cars.exception.ResourceNotFoundException;
import de.evolvice.cars.service.BodyTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * Car BodyType Rest Controller
 * 
 * @author SayedBaladoh
 *
 */
@RestController
@RequestMapping("/api/bodyTypes")
@Api(
		value = "BodyType",
		description = "Operations for car body type basic data")
public class BodyTypeController {

	@Autowired
	private BodyTypeService bodyTypeService;

	@ApiOperation(
			value = "View a page of list of available car body types",
			response = Page.class)
	@ApiResponses(
			value = {
					@ApiResponse(
							code = 200,
							message = "Successfully retrieved a page"),
					@ApiResponse(
							code = 401,
							message = "You are not authorized to view the resource"),
					@ApiResponse(
							code = 403,
							message = "Accessing the resource you were trying to reach is forbidden"),
					@ApiResponse(
							code = 404,
							message = "The resource you were trying to reach is not found")
			})
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	@GetMapping()
	public Page<BodyType> getBodyTypes(Pageable pageable) {
		return bodyTypeService.getAll(pageable);
	}

	@ApiOperation(
			value = "Search a car body type with an ID",
			response = BodyType.class)
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	@GetMapping("/{bodyTypeId}")
	public BodyType getBodyType(@PathVariable(
			name = "bodyTypeId") Long bodyTypeId) {
		return bodyTypeService.get(bodyTypeId)
				.orElseThrow(() -> new ResourceNotFoundException("BodyType", "Id", bodyTypeId));
	}

	@ApiOperation(
			value = "Add a car body type",
			response = BodyType.class)
	@PostMapping()
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public BodyType addBodyType(@Valid @RequestBody BodyType bodyType) {
		try {
			return bodyTypeService.save(bodyType);
		} catch (DataIntegrityViolationException ex) {
			throw new BadRequestException("Sorry! You have already bodyType with the same id or name.");
		}
	}

	@ApiOperation(
			value = "Edit a car body type",
			response = BodyType.class)
	@PutMapping("/{bodyTypeId}")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public BodyType updateBodyType(@PathVariable(
			name = "bodyTypeId") Long bodyTypeId,
			@RequestBody BodyType bodyTypeRequest) {
		return bodyTypeService.get(bodyTypeId).map(bodyType -> {
			if (bodyTypeRequest.getName() != null)
				if (bodyTypeRequest.getName().equals(""))
					throw new BadRequestException("Sorry! The name must not be blank");
				else
					bodyType.setName(bodyTypeRequest.getName());
			try {
				return bodyTypeService.save(bodyType);
			} catch (DataIntegrityViolationException ex) {
				throw new BadRequestException("Sorry! You have already bodyType with the same name.");
			}
		}).orElseThrow(() -> new ResourceNotFoundException("BodyType", "Id", bodyTypeId));
	}

	@ApiOperation(
			value = "Delete a car body type")
	@DeleteMapping("/{bodyTypeId}")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public ResponseEntity<?> deleteBodyType(@PathVariable(
			name = "bodyTypeId") Long bodyTypeId) {
		return bodyTypeService.get(bodyTypeId).map(bodyType -> {
			bodyTypeService.delete(bodyTypeId);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("BodyType", "Id", bodyTypeId));
	}
}
