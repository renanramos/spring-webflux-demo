package br.com.renanrramos.springwebfluxdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.renanrramos.springwebfluxdemo.messages.constants.Messages;
import br.com.renanrramos.springwebfluxdemo.model.Employee;
import br.com.renanrramos.springwebfluxdemo.repository.EmployeeRepository;
import br.com.renanrramos.springwebfluxdemo.service.EmployeeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService{

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
	public Mono<Employee> findById(final Integer employeeId) {
		return Mono.justOrEmpty(employeeRepository.findById(employeeId));
	}

	@Override
	public void removeEmployee(final Integer employeeId) {
		findById(employeeId)
			.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.EMPLOYEE_NOT_FOUND)))
			.blockOptional()
			.ifPresent((employee) -> employeeRepository.deleteById(employee.getId()));
	}

	@Override
	public Employee update(final Employee employee) {
		return employeeRepository.save(employee);
	}	
}
