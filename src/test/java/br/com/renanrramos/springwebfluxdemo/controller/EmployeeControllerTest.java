package br.com.renanrramos.springwebfluxdemo.controller;


import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_ID;
import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_NAME;
import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_DEPARTMENT;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.springwebfluxdemo.common.CommonUtils;
import br.com.renanrramos.springwebfluxdemo.controller.rest.EmployeeController;
import br.com.renanrramos.springwebfluxdemo.form.EmployeeForm;
import br.com.renanrramos.springwebfluxdemo.messages.constants.Messages;
import br.com.renanrramos.springwebfluxdemo.model.Employee;
import br.com.renanrramos.springwebfluxdemo.service.EmployeeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeControllerTest {

	@InjectMocks
	private EmployeeController employeeController;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private UriComponentsBuilder uriBuilder;

	@Mock
	private UriComponents uriComponents;

	private URI uri;

	@Test
	public void create_withValidEmployee_shouldCreateSuccessfully() {		
		when(employeeService.create(any(EmployeeForm.class))).thenReturn(CommonUtils.getEmployeeInstance());
		mockUriComponentsBuilder();

		ResponseEntity<Employee> responseEntity = employeeController.create(CommonUtils.getEmployeeFormInstance(), uriBuilder);

		assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));
		assertThat(responseEntity.getBody().getId(), is(EMPLOYEE_ID));
		assertThat(responseEntity.getBody().getName(), is(EMPLOYEE_NAME));
		assertThat(responseEntity.getBody().getDepartment(), is(EMPLOYEE_DEPARTMENT));
		verify(employeeService, times(1)).create(any(EmployeeForm.class));
	}

	@Test
	public void create_withEmptyEmployeeName_shouldReturnBadRequest() {

		ResponseStatusException badRequestException = getResponseStatusException(HttpStatus.BAD_REQUEST, Messages.INVALID_EMPLOYEE_FORM);
		EmployeeForm invalidForm = EmployeeForm.builder().name("").department(EMPLOYEE_DEPARTMENT).build();

		when(employeeService.create(any(EmployeeForm.class))).thenThrow(badRequestException);

		try {
			employeeController.create(invalidForm, uriBuilder);			
		} catch (ResponseStatusException e) {
			
			assertThat(e.getStatus(), equalTo(badRequestException.getStatus()));
			assertThat(e.getReason(), equalTo(badRequestException.getReason()));

			verify(uriBuilder, never()).path(anyString());
			verify(uriBuilder, never()).buildAndExpand(anyInt());
			verify(uriComponents, never()).encode();
			verify(uriComponents, never()).toUri();
		}
	}

	@Test
	public void create_withEmptyEmployeeDepartment_shouldReturnBadRequest() {

		ResponseStatusException badRequestException = getResponseStatusException(HttpStatus.BAD_REQUEST, Messages.INVALID_EMPLOYEE_FORM);
		EmployeeForm invalidForm = EmployeeForm.builder().name(EMPLOYEE_NAME).department("").build();

		when(employeeService.create(any(EmployeeForm.class))).thenThrow(badRequestException);

		try {
			employeeController.create(invalidForm, uriBuilder);			
		} catch (ResponseStatusException e) {
			
			assertThat(e.getStatus(), equalTo(badRequestException.getStatus()));
			assertThat(e.getReason(), equalTo(badRequestException.getReason()));

			verify(uriBuilder, never()).path(anyString());
			verify(uriBuilder, never()).buildAndExpand(anyInt());
			verify(uriComponents, never()).encode();
			verify(uriComponents, never()).toUri();
		}
	}

	@Test
	public void findAll_withInitialEmployeesList_shouldReturnListOfEmployees() {

		List<Employee> employees = new ArrayList<>();
		employees.add(CommonUtils.getEmployeeInstance());

		when(employeeService.findAll()).thenReturn(Flux.just(employees));

		Flux<List<Employee>> employeesResponse = employeeController.findAll();

		assertThat(employeesResponse.blockLast().size(), is(1));
		assertThat(employeesResponse.blockLast().get(0).getId(), is(EMPLOYEE_ID));
		assertThat(employeesResponse.blockLast().get(0).getName(), is(EMPLOYEE_NAME));
		assertThat(employeesResponse.blockLast().get(0).getDepartment(), is(EMPLOYEE_DEPARTMENT));
	}

	@Test
	public void findEmployee_whenEmployeeIdIsValid_shouldReturnEmployee() {
		when(employeeService.findById(anyInt())).thenReturn(Mono.just(CommonUtils.getEmployeeInstance()));

		ResponseEntity<Employee> responseEntity = employeeController.findEmployee(EMPLOYEE_ID);

		assertThat(responseEntity.getBody().getId(), is(EMPLOYEE_ID));
		assertThat(responseEntity.getBody().getName(), is(EMPLOYEE_NAME));
		assertThat(responseEntity.getBody().getDepartment(), is(EMPLOYEE_DEPARTMENT));
	}

	@Test
	public void findEmployee_whenEmployeeNotFound_shouldReturnNotFound() {
		ResponseStatusException notFoundExcepton = getResponseStatusException(HttpStatus.NOT_FOUND, Messages.EMPLOYEE_NOT_FOUND);
		when(employeeService.findById(anyInt())).thenThrow(notFoundExcepton);

		try {
			employeeController.findEmployee(EMPLOYEE_ID);			
		}catch (ResponseStatusException e) {			
			assertThat(e.getStatus(), equalTo(notFoundExcepton.getStatus()));
			assertThat(e.getReason(), equalTo(notFoundExcepton.getReason()));
			verify(employeeService, times(1)).findById(anyInt());
		}
	}

	@Test
	public void removeEmployee_withValidEmployeeId_shouldRemoveSuccessfully() {
		ResponseEntity<Object> responseEntity = employeeController.removeEmployee(EMPLOYEE_ID);

		assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
		verify(employeeService, times(1)).removeEmployee(anyInt());
	}

	@Test
	public void removeEmployee_withInvalidEmployeeId_shouldReturnNotFound() {
		ResponseStatusException notFoundExcepton = getResponseStatusException(HttpStatus.NOT_FOUND, Messages.EMPLOYEE_NOT_FOUND);

		try {			
			employeeController.removeEmployee(EMPLOYEE_ID);
		}catch (ResponseStatusException e) {
			assertThat(e.getStatus(), equalTo(notFoundExcepton.getStatus()));
			assertThat(e.getReason(), equalTo(notFoundExcepton.getReason()));
			verify(employeeService, never()).removeEmployee(anyInt());
		}
	}

	@Test
	public void updateEmployee_withValidEmployee_shouldUpdateSuccessfully() {
		Employee updatedEmployee = CommonUtils.getUpdatedEmployee();
		when(employeeService.update(any(Employee.class))).thenReturn(updatedEmployee);
		mockUriComponentsBuilder();

		ResponseEntity<Employee> response = employeeController.updateEmployee(EMPLOYEE_ID, CommonUtils.getEmployeeFormInstance(), uriBuilder);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.ACCEPTED));		
		assertThat(response.getBody().getId(), equalTo(EMPLOYEE_ID));
		assertThat(response.getBody().getName(), equalTo(updatedEmployee.getName()));
		assertThat(response.getBody().getDepartment(), equalTo(updatedEmployee.getDepartment()));
	}

	private void mockUriComponentsBuilder() {
		when(uriBuilder.path(anyString())).thenReturn(uriBuilder);
		when(uriBuilder.buildAndExpand(anyInt())).thenReturn(uriComponents);
		when(uriComponents.encode()).thenReturn(uriComponents);
		when(uriComponents.toUri()).thenReturn(uri);
	}

	private ResponseStatusException getResponseStatusException(final HttpStatus status, final String reason) {
		return new ResponseStatusException(status, reason);
	}
}
