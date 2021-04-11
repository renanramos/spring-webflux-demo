package br.com.renanrramos.springwebfluxdemo.infra.service.impl;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.springwebfluxdemo.application.messages.constants.Messages;
import br.com.renanrramos.springwebfluxdemo.application.model.Employee;
import br.com.renanrramos.springwebfluxdemo.infra.repository.EmployeeRepository;
import br.com.renanrramos.springwebfluxdemo.infra.service.EmployeeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee create(final Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public Flux<Employee> findAll() {
		return Flux.fromIterable(employeeRepository.findAll());
	}

	@Override
	public Mono<Employee> findById(final UUID employeeId) {
		return Mono.justOrEmpty(employeeRepository.findById(employeeId));
	}

	@Override
	public void remove(final UUID employeeId) {
		findById(employeeId)
		.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.EMPLOYEE_NOT_FOUND)))
		.blockOptional()
		.ifPresent((employee) -> employeeRepository.deleteById(employeeId));
	}

	@Override
	public Employee update(final Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public URI buildEmployeeUri(final UriComponentsBuilder uriBuilder, final UUID employeeId) {
		return uriBuilder.path("/employees/{id}").buildAndExpand(employeeId).encode().toUri();
	}
}
