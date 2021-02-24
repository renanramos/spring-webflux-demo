package br.com.renanrramos.springwebfluxdemo.service;

import br.com.renanrramos.springwebfluxdemo.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {
	
	Employee create(Employee e);

	Mono<Employee> findById(Integer employeeId);
	
	Flux<Employee> findAll();

	void removeEmployee(Integer employeeId);

	Employee update(Employee employee);
}
