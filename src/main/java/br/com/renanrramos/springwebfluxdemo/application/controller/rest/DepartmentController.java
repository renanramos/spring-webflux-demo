package br.com.renanrramos.springwebfluxdemo.application.controller.rest;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.springwebfluxdemo.application.controller.validator.DepartmentValidator;
import br.com.renanrramos.springwebfluxdemo.application.form.DepartmentForm;
import br.com.renanrramos.springwebfluxdemo.application.messages.constants.Messages;
import br.com.renanrramos.springwebfluxdemo.application.model.Department;
import br.com.renanrramos.springwebfluxdemo.infra.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "api/departments", produces = "application/json")
@CrossOrigin(origins = "*")
@Api(tags = "Departments")
public class DepartmentController {
	private static final String CONTEXT_PATH = "/departments/{id}";

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private DepartmentValidator departmentValidator;

	@ResponseBody
	@PostMapping
	@ApiOperation(value = "Add new department")
	public ResponseEntity<Mono<Department>> create(@Valid @RequestBody DepartmentForm departmentForm,
			final UriComponentsBuilder uriBuilder) {
		final Department department = new Department().withName(departmentForm.getName());

		final Department response = departmentService.create(department)
				.blockOptional()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.INVALID_FORM));

		return ResponseEntity.status(HttpStatus.CREATED)
				.location(departmentService.buildEmployeeUri(CONTEXT_PATH, uriBuilder, response.getId()))
				.body(Mono.just(response));
	}

	@ResponseBody
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get all departments")
	public Flux<Department> findAll()
	{
		return departmentService.findAll();
	}

	@ResponseBody
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get department by id")
	public ResponseEntity<Mono<Department>> findDepartment(@PathVariable("id") final UUID departmentId)
	{
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(departmentService.findById(departmentId)
						.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.DEPARTMENT_NOT_FOUND))));
	}

	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Delete a department")
	public ResponseEntity<Object> removeDepartment(@PathVariable("id") final UUID departmentId) {
		departmentService.remove(departmentId);
		return ResponseEntity.ok().build();
	}

	@ResponseBody
	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Update a department")
	public ResponseEntity<Mono<Department>> updateDepartment(@PathVariable("id") final UUID departmentId,
			@RequestBody DepartmentForm departmentForm, final UriComponentsBuilder uriBuilder) {

		departmentValidator.validate(departmentId);

		final Department departmentToUpdate = new Department().withId(departmentId).withName(departmentForm.getName());

		final Department response = departmentService.update(departmentToUpdate);

		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.location(departmentService.buildEmployeeUri(CONTEXT_PATH, uriBuilder, departmentId))
				.body(Mono.just(response));
	}
}
