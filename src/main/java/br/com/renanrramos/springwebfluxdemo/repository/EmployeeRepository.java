package br.com.renanrramos.springwebfluxdemo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import br.com.renanrramos.springwebfluxdemo.messages.constants.Messages;
import br.com.renanrramos.springwebfluxdemo.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EmployeeRepository {

    @Value("${initial.employees}")
    private int initialEmployees;

	private List<Employee> EMPLOYEES = new ArrayList<>();

	public EmployeeRepository() {
	}

	public Employee save(Employee e) {
		return addEmpolyee(e);
	}

	public Mono<Employee> findById(Integer id) {
		return getEmployeeById(id);		
	}

	public Flux<List<Employee>> findAll() {
		return Flux.just(EMPLOYEES);
	}

	public void removeEmployee(int id) {
		getEmployeeById(id)
			.blockOptional()
			.ifPresent(emp -> EMPLOYEES.remove(id));
	}

	public Employee update(Employee e) {
		
		Employee employeeToBeUpdated = getEmployeeById(e.getId()).blockOptional().get();
		BeanUtils.copyProperties(e, employeeToBeUpdated);

		int index = EMPLOYEES.indexOf(employeeToBeUpdated);
		
		EMPLOYEES.set(index, employeeToBeUpdated);
		
		return employeeToBeUpdated;
	}

    @Bean
	public void createEmployeesList() {
		for (int i = 0; i <= initialEmployees ; i++) {
			EMPLOYEES.add(employeeGenerator(i));
		}
	}

	private Mono<Employee> getEmployeeById(int id) {
		Optional<Employee> employeeOptional = EMPLOYEES.stream().filter(emp -> emp.getId() == id).findFirst();

		if (!employeeOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.EMPLOYEE_NOT_FOUND);
		}

		return Mono.just(employeeOptional.get());
	}

	private Employee addEmpolyee(Employee employee) {
		employee.setId(EMPLOYEES.size());
		EMPLOYEES.add(employee);
		return employee;
	}

	private static Employee employeeGenerator(int id) {
		return Employee.builder()
				.department("Department " + id)
				.id(id).name("Empl " + id)
				.build();
	}
}
