package br.com.renanrramos.springwebfluxdemo.repository;

import org.springframework.data.repository.CrudRepository;
import br.com.renanrramos.springwebfluxdemo.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
}
