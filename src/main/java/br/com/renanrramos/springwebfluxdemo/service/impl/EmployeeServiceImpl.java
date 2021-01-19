package br.com.renanrramos.springwebfluxdemo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanrramos.springwebfluxdemo.form.EmployeeForm;
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
	public Employee create(EmployeeForm e) {
		Employee employee = Employee.builder()
				.name(e.getName())
				.department(e.getDepartment())
				.build();
		return employeeRepository.save(employee);
	}

	@Override
	public Flux<List<Employee>> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Mono<Employee> findById(Integer id) {
		return employeeRepository.findById(id);
	}

	@Override
	public void removeEmployee(int id) {
		employeeRepository.removeEmployee(id);
	}	
}
