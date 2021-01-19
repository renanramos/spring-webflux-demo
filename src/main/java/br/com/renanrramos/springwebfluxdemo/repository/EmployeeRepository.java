package br.com.renanrramos.springwebfluxdemo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
		EMPLOYEES.remove(id);
	}

    @Bean
	public void createEmployeesList() {
		for (int i = 0; i <= initialEmployees ; i++) {
			EMPLOYEES.add(employeeGenerator(i));
		}
	}

	private Mono<Employee> getEmployeeById(Integer id) {
		Optional<Employee> employeeOptional = EMPLOYEES
				.stream()
				.filter(employee -> employee.getId().equals(id))
				.findFirst();
		
		return employeeOptional.isPresent() ?
				Mono.just(employeeOptional.get()) :
				Mono.empty();
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
