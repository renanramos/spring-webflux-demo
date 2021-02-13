package br.com.renanrramos.springwebfluxdemo.service;

import java.util.List;

import br.com.renanrramos.springwebfluxdemo.form.EmployeeForm;
import br.com.renanrramos.springwebfluxdemo.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {
	
	Employee create(EmployeeForm e);

	Mono<Employee> findById(Integer id);
	
	Flux<List<Employee>> findAll();

	void removeEmployee(Integer id);

	Employee update(Employee e);
}
