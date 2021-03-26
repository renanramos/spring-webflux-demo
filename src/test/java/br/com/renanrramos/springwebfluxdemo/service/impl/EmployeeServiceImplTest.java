package br.com.renanrramos.springwebfluxdemo.service.impl;

import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_DEPARTMENT;
import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_ID;
import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_NAME;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.renanrramos.springwebfluxdemo.common.CommonUtils;
import br.com.renanrramos.springwebfluxdemo.model.Employee;
import br.com.renanrramos.springwebfluxdemo.repository.EmployeeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeServiceImplTest {

	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;

	@Mock
	private EmployeeRepository mockEmployeeRepository;
	
	@Test
	public void create_withEmployee_shouldReturnEmployee() {
		Employee employee = CommonUtils.getEmployeeInstance();
		when(mockEmployeeRepository.save(any(Employee.class))).thenReturn(employee);

		Employee employeeCreated = employeeServiceImpl.create(employee);

		checkEmployee(employeeCreated);
	}

	@Test
	public void findAll_withEmployeeInitialized_shouldReturnListSuccessfully() {

		when(mockEmployeeRepository.findAll()).thenReturn(Arrays.asList(CommonUtils.getEmployeeInstance()));
		
		Flux<Employee> fluxEmployees = employeeServiceImpl.findAll();
		List<Employee> employees = fluxEmployees.toStream().collect(Collectors.toList());

		assertThat(employees.size(), is(1));
		checkEmployee(fluxEmployees.blockFirst());
	}

	@Test
	public void findById_withValidEmployeeId_shouldReturnEmployeeSuccessfully() {
		when(mockEmployeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(CommonUtils.getEmployeeInstance()));

		Mono<Employee> employeeFound = employeeServiceImpl.findById(EMPLOYEE_ID);

		checkEmployee(employeeFound.block());
	}

	@Test
	public void removeEmployee_withEmployeeId_shouldRemoveSuccessfully() {
		when(mockEmployeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(CommonUtils.getEmployeeInstance()));
		employeeServiceImpl.removeEmployee(EMPLOYEE_ID);
		verify(mockEmployeeRepository, times(1)).deleteById(EMPLOYEE_ID);
	}

	@Test
	public void updateEmployee_withValidEmployee_shouldUpdateSuccessfully() {
		Employee employee = CommonUtils.getEmployeeInstance();
		employee.setId(EMPLOYEE_ID);
		
		when(mockEmployeeRepository.save(any(Employee.class))).thenReturn(CommonUtils.getEmployeeInstance());
		
		Employee updatedEmployee = employeeServiceImpl.update(employee);

		checkEmployee(updatedEmployee);
	}

	private void checkEmployee(Employee updatedEmployee) {
		assertThat(updatedEmployee.getId(), is(EMPLOYEE_ID));
		assertThat(updatedEmployee.getName(), is(EMPLOYEE_NAME));
		assertThat(updatedEmployee.getDepartment(), is(EMPLOYEE_DEPARTMENT));
	}
}
