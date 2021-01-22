package br.com.renanrramos.springwebfluxdemo.controller.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
	public ResponseEntity<Employee> create(@Valid @RequestBody EmployeeForm employee, UriComponentsBuilder uriBuilder) {
		Optional<Employee> empOptional = Optional.ofNullable(employeeService.create(employee));
		
		empOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.INVALID_EMPLOYEE_FORM));

		uri = uriBuilder.path("/employees/{id}")
				.buildAndExpand(empOptional.get().getId())
				.encode()
				.toUri();
		
		return ResponseEntity
			.status(HttpStatus.CREATED)
				.location(uri).body(empOptional.get());
	}

	@ResponseBody
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get all employees")
	public Flux<List<Employee>> findAll() {
		return employeeService.findAll();
	}

	@ResponseBody
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get employee by id")
	public ResponseEntity<Employee> findEmployee(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(employeeService.findById(id).block());
	}

	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Delete an employee")
	public ResponseEntity<Object> removeEmployee(@PathVariable("id") int id) {
		employeeService.removeEmployee(id);
		return ResponseEntity.ok().build();
	}

	@ResponseBody
	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Update an employee")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int id, @RequestBody EmployeeForm employeeForm) {
		Employee employee = Employee.builder()
				.id(id)
				.name(employeeForm.getName())
				.department(employeeForm.getDepartment())
				.build();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.update(employee));
	}
}
