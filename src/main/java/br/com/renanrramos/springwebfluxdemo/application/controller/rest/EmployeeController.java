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
import br.com.renanrramos.springwebfluxdemo.application.form.EmployeeForm;
import br.com.renanrramos.springwebfluxdemo.application.messages.constants.Messages;
import br.com.renanrramos.springwebfluxdemo.application.model.Department;
import br.com.renanrramos.springwebfluxdemo.application.model.Employee;
import br.com.renanrramos.springwebfluxdemo.infra.service.DepartmentService;
import br.com.renanrramos.springwebfluxdemo.infra.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "api/employees", produces = "application/json")
@CrossOrigin(origins = "*")
@Api(tags = "Employees")
public class EmployeeController {

	private static final String CONTEXT_PATH = "/employees/{id}";

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private DepartmentValidator departmentValidator;

	@ResponseBody
	@PostMapping
	@ApiOperation(value = "Add new employee")
	public ResponseEntity<Mono<Employee>> create(@Valid @RequestBody final EmployeeForm employeeForm, final UriComponentsBuilder uriBuilder) {

		departmentValidator.validate(employeeForm.getDepartmentId());

		final Department department = departmentService.findById(employeeForm.getDepartmentId()).block();

		final Employee employee = new Employee().withDepartment(department);

		final Employee response = employeeService.create(employee)
				.blockOptional()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.INVALID_FORM));

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.location(employeeService.buildEmployeeUri(CONTEXT_PATH, uriBuilder, response.getId()))
				.body(Mono.just(response));
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
		employeeService.remove(employeeId);
		return ResponseEntity.ok().build();
	}

	@ResponseBody
	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Update an employee")
	public ResponseEntity<Mono<Employee>> updateEmployee(@PathVariable("id") final UUID employeeId, @RequestBody final EmployeeForm employeeForm, final UriComponentsBuilder uriBuilder) {

		departmentValidator.validate(employeeForm.getDepartmentId());
		final Department department = departmentService.findById(employeeForm.getDepartmentId()).block();
		final Employee employee = new Employee().withDepartment(department).withName(employeeForm.getName());

		final Employee updatedEmployee = employeeService.update(employee);

		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.location(employeeService.buildEmployeeUri(CONTEXT_PATH, uriBuilder, updatedEmployee.getId()))
				.body(Mono.just(updatedEmployee));
	}
}
