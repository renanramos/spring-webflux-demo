package br.com.renanrramos.springwebfluxdemo.service.impl;

import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_DEPARTMENT;
import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_ID;
import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_NAME;
import static br.com.renanrramos.springwebfluxdemo.common.Constants.UPDATED_EMPLOYEE_NAME;
import static br.com.renanrramos.springwebfluxdemo.common.Constants.UPDATED_EMPLOYEE_DEPARTMENT;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;

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

		Employee employeeCreated = employeeServiceImpl.create(CommonUtils.getEmployeeFormInstance());

		assertThat(employeeCreated.getId(), is(EMPLOYEE_ID));
		assertThat(employeeCreated.getName(), is(EMPLOYEE_NAME));
		assertThat(employeeCreated.getDepartment(), is(EMPLOYEE_DEPARTMENT));
	}

	@Test
	public void findAll_withEmployeeInitialized_shouldReturnListSuccessfully() {

		when(mockEmployeeRepository.findAll()).thenReturn(Flux.just(Arrays.asList(CommonUtils.getEmployeeInstance())));
		
		Flux<List<Employee>> fluxEmployees = employeeServiceImpl.findAll();

		assertThat(fluxEmployees.blockFirst().size(), is(1));
		assertThat(fluxEmployees.blockFirst().get(0).getId(), is(EMPLOYEE_ID));
		assertThat(fluxEmployees.blockFirst().get(0).getName(), is(EMPLOYEE_NAME));
		assertThat(fluxEmployees.blockFirst().get(0).getDepartment(), is(EMPLOYEE_DEPARTMENT));
	}

	@Test
	public void findById_withValidEmployeeId_shouldReturnEmployeeSuccessfully() {
		when(mockEmployeeRepository.findById(anyInt())).thenReturn(Mono.just(CommonUtils.getEmployeeInstance()));

		Mono<Employee> employeeFound = employeeServiceImpl.findById(anyInt());

		assertThat(employeeFound.block().getId(), is(EMPLOYEE_ID));
		assertThat(employeeFound.block().getName(), is(EMPLOYEE_NAME));
		assertThat(employeeFound.block().getDepartment(), is(EMPLOYEE_DEPARTMENT));
	}

	@Test
	public void removeEmployee_withEmployeeId_shouldRemoveSuccessfully() {
		employeeServiceImpl.removeEmployee(anyInt());
		verify(mockEmployeeRepository, times(1)).removeEmployee(anyInt());
	}

	@Test
	public void updateEmployee_withValidEmployee_shouldUpdateSuccessfully() {
		Employee employee = CommonUtils.getEmployeeInstance();
		employee.setId(EMPLOYEE_ID);
		
		when(mockEmployeeRepository.update(any(Employee.class))).thenReturn(CommonUtils.getUpdatedEmployee());
		
		Employee updatedEmployee = employeeServiceImpl.update(employee);

		assertThat(updatedEmployee.getId(), is(EMPLOYEE_ID));
		assertThat(updatedEmployee.getName(), is(UPDATED_EMPLOYEE_NAME));
		assertThat(updatedEmployee.getDepartment(), is(UPDATED_EMPLOYEE_DEPARTMENT));
	}
}
