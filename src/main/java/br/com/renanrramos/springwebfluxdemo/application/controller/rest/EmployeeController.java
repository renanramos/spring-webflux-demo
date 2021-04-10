package br.com.renanrramos.springwebfluxdemo.controller.rest;

import java.net.URI;
import java.util.Optional;
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

import br.com.renanrramos.springwebfluxdemo.form.EmployeeForm;
import br.com.renanrramos.springwebfluxdemo.messages.constants.Messages;
import br.com.renanrramos.springwebfluxdemo.model.Employee;
import br.com.renanrramos.springwebfluxdemo.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "api/employees", produces = "application/json")
@CrossOrigin(origins = "*")
@Api(tags = "Employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	private URI uri;

	@ResponseBody
	@PostMapping
	@ApiOperation(value = "Add new employee")
	public ResponseEntity<Mono<Employee>> create(@Valid @RequestBody final EmployeeForm employeeForm, final UriComponentsBuilder uriBuilder) {
		
		Employee employee = Employee.builder()
				.department(employeeForm.getDepartment())
				.name(employeeForm.getName())
				.build();
		
		Optional<Employee> empOptional = Optional.ofNullable(employeeService.create(employee));
		
		empOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.INVALID_EMPLOYEE_FORM));

		uri = createEmployeeUri(uriBuilder, empOptional.get().getId());

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.location(uri)
				.body(Mono.just(empOptional.get()));
	}

	@ResponseBody
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get all employees")
	public Flux<Employee> findAll() {
		return employeeService.findAll();
	}

	@ResponseBody
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get employee by id")
	public ResponseEntity<Mono<Employee>> findEmployee(@PathVariable("id") final UUID employeeId) {
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(employeeService
							.findById(employeeId)
							.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.EMPLOYEE_NOT_FOUND))));
	}

	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Delete an employee")
	public ResponseEntity<Object> removeEmployee(@PathVariable("id") final UUID employeeId) {
		employeeService.removeEmployee(employeeId);
		return ResponseEntity.ok().build();
	}

	@ResponseBody
	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Update an employee")
	public ResponseEntity<Mono<Employee>> updateEmployee(@PathVariable("id") final UUID employeeId, @RequestBody final EmployeeForm employeeForm, final UriComponentsBuilder uriBuilder) {
		Employee employee = Employee.builder()
				.id(employeeId)
				.name(employeeForm.getName())
				.department(employeeForm.getDepartment())
				.build();
		
		Employee updatedEmployee = employeeService.update(employee);
		
		uri = createEmployeeUri(uriBuilder, updatedEmployee.getId());
		
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.location(uri)
				.body(Mono.just(updatedEmployee));
	}

	private URI createEmployeeUri(final UriComponentsBuilder uriBuilder, final UUID employeeId) {
		return uriBuilder.path("/employees/{id}")
				.buildAndExpand(employeeId)
				.encode()
				.toUri();
	}
}
