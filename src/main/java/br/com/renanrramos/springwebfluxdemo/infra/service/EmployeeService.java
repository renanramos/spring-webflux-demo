package br.com.renanrramos.springwebfluxdemo.infra.service;

import java.net.URI;
import java.util.UUID;

import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.springwebfluxdemo.application.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

	Employee create(Employee e);

	Mono<Employee> findById(UUID employeeId);

	Flux<Employee> findAll();

	void removeEmployee(UUID employeeId);

	Employee update(Employee employee);

	URI buildEmployeeUri(final UriComponentsBuilder uriBuilder, final UUID employeeId);
}
