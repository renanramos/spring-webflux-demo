package br.com.renanrramos.springwebfluxdemo.service;

import java.util.UUID;

import br.com.renanrramos.springwebfluxdemo.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {
	
	Employee create(Employee e);

	Mono<Employee> findById(UUID employeeId);
	
	Flux<Employee> findAll();

	void removeEmployee(UUID employeeId);

	Employee update(Employee employee);
}
