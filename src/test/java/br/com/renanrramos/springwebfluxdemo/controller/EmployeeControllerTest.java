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
import java.util.UUID;
import java.util.stream.Collectors;

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
	private EmployeeService mockEmployeeService;

	@Mock
	private UriComponentsBuilder mockUriBuilder;

	@Mock
	private UriComponents mockUriComponents;

	private URI uri;

	@Test
	public void create_withValidEmployee_shouldCreateSuccessfully() {		
		when(mockEmployeeService.create(any(Employee.class))).thenReturn(CommonUtils.getEmployeeInstance());
		mockUriComponentsBuilder();

		ResponseEntity<Mono<Employee>> responseEntity = employeeController.create(CommonUtils.getEmployeeFormInstance(), mockUriBuilder);

		assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));
		assertThat(responseEntity.getBody().block().getId(), is(EMPLOYEE_ID));
		assertThat(responseEntity.getBody().block().getName(), is(EMPLOYEE_NAME));
		assertThat(responseEntity.getBody().block().getDepartment(), is(EMPLOYEE_DEPARTMENT));
		verify(mockEmployeeService, times(1)).create(any(Employee.class));
	}

	@Test
	public void create_withEmptyEmployeeName_shouldReturnBadRequest() {

		ResponseStatusException badRequestException = getResponseStatusException(HttpStatus.BAD_REQUEST, Messages.INVALID_EMPLOYEE_FORM);
		EmployeeForm invalidForm = EmployeeForm.builder().name("").department(EMPLOYEE_DEPARTMENT).build();

		when(mockEmployeeService.create(any(Employee.class))).thenThrow(badRequestException);

		try {
			employeeController.create(invalidForm, mockUriBuilder);			
		} catch (ResponseStatusException e) {
			
			assertThat(e.getStatus(), equalTo(badRequestException.getStatus()));
			assertThat(e.getReason(), equalTo(badRequestException.getReason()));

			verify(mockUriBuilder, never()).path(anyString());
			verify(mockUriBuilder, never()).buildAndExpand(anyInt());
			verify(mockUriComponents, never()).encode();
			verify(mockUriComponents, never()).toUri();
		}
	}

	@Test
	public void create_withEmptyEmployeeDepartment_shouldReturnBadRequest() {

		ResponseStatusException badRequestException = getResponseStatusException(HttpStatus.BAD_REQUEST, Messages.INVALID_EMPLOYEE_FORM);
		EmployeeForm invalidForm = EmployeeForm.builder().name(EMPLOYEE_NAME).department("").build();

		when(mockEmployeeService.create(any(Employee.class))).thenThrow(badRequestException);

		try {
			employeeController.create(invalidForm, mockUriBuilder);			
		} catch (ResponseStatusException e) {
			
			assertThat(e.getStatus(), equalTo(badRequestException.getStatus()));
			assertThat(e.getReason(), equalTo(badRequestException.getReason()));

			verify(mockUriBuilder, never()).path(anyString());
			verify(mockUriBuilder, never()).buildAndExpand(anyInt());
			verify(mockUriComponents, never()).encode();
			verify(mockUriComponents, never()).toUri();
		}
	}

	@Test
	public void findAll_withInitialEmployeesList_shouldReturnListOfEmployees() {

		List<Employee> employees = new ArrayList<>();
		employees.add(CommonUtils.getEmployeeInstance());

		when(mockEmployeeService.findAll()).thenReturn(Flux.fromIterable(employees));

		Flux<Employee> employeesResponse = employeeController.findAll();
		List<Employee> employeesList = employeesResponse.toStream().collect(Collectors.toList());

		assertThat(employeesList.size(), is(1));
		assertThat(employeesResponse.blockFirst().getId(), is(EMPLOYEE_ID));
		assertThat(employeesResponse.blockFirst().getName(), is(EMPLOYEE_NAME));
		assertThat(employeesResponse.blockFirst().getDepartment(), is(EMPLOYEE_DEPARTMENT));
	}

	@Test
	public void findEmployee_whenEmployeeIdIsValid_shouldReturnEmployee() {
		when(mockEmployeeService.findById(EMPLOYEE_ID)).thenReturn(Mono.just(CommonUtils.getEmployeeInstance()));

		ResponseEntity<Mono<Employee>> responseEntity = employeeController.findEmployee(EMPLOYEE_ID);

		assertThat(responseEntity.getBody().block().getId(), is(EMPLOYEE_ID));
		assertThat(responseEntity.getBody().block().getName(), is(EMPLOYEE_NAME));
		assertThat(responseEntity.getBody().block().getDepartment(), is(EMPLOYEE_DEPARTMENT));
	}

	@Test
	public void findEmployee_whenEmployeeNotFound_shouldReturnNotFound() {
		ResponseStatusException notFoundExcepton = getResponseStatusException(HttpStatus.NOT_FOUND, Messages.EMPLOYEE_NOT_FOUND);
		when(mockEmployeeService.findById(EMPLOYEE_ID)).thenThrow(notFoundExcepton);

		try {
			employeeController.findEmployee(EMPLOYEE_ID);			
		}catch (ResponseStatusException e) {			
			assertThat(e.getStatus(), equalTo(notFoundExcepton.getStatus()));
			assertThat(e.getReason(), equalTo(notFoundExcepton.getReason()));
			verify(mockEmployeeService, times(1)).findById(EMPLOYEE_ID);
		}
	}

	@Test
	public void removeEmployee_withValidEmployeeId_shouldRemoveSuccessfully() {
		ResponseEntity<Object> responseEntity = employeeController.removeEmployee(EMPLOYEE_ID);

		assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
		verify(mockEmployeeService, times(1)).removeEmployee(EMPLOYEE_ID);
	}

	@Test
	public void removeEmployee_withInvalidEmployeeId_shouldReturnNotFound() {
		ResponseStatusException notFoundExcepton = getResponseStatusException(HttpStatus.NOT_FOUND, Messages.EMPLOYEE_NOT_FOUND);

		try {			
			employeeController.removeEmployee(EMPLOYEE_ID);
		}catch (ResponseStatusException e) {
			assertThat(e.getStatus(), equalTo(notFoundExcepton.getStatus()));
			assertThat(e.getReason(), equalTo(notFoundExcepton.getReason()));
			verify(mockEmployeeService, never()).removeEmployee(EMPLOYEE_ID);
		}
	}

	@Test
	public void updateEmployee_withValidEmployee_shouldUpdateSuccessfully() {
		Employee updatedEmployee = CommonUtils.getEmployeeInstance();
		when(mockEmployeeService.update(any(Employee.class))).thenReturn(updatedEmployee);
		mockUriComponentsBuilder();

		ResponseEntity<Mono<Employee>> response = employeeController.updateEmployee(EMPLOYEE_ID, CommonUtils.getEmployeeFormInstance(), mockUriBuilder);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.ACCEPTED));		
		assertThat(response.getBody().block().getId(), equalTo(EMPLOYEE_ID));
		assertThat(response.getBody().block().getName(), equalTo(updatedEmployee.getName()));
		assertThat(response.getBody().block().getDepartment(), equalTo(updatedEmployee.getDepartment()));
	}

	private void mockUriComponentsBuilder() {
		when(mockUriBuilder.path(anyString())).thenReturn(mockUriBuilder);
		when(mockUriBuilder.buildAndExpand(EMPLOYEE_ID)).thenReturn(mockUriComponents);
		when(mockUriComponents.encode()).thenReturn(mockUriComponents);
		when(mockUriComponents.toUri()).thenReturn(uri);
	}

	private ResponseStatusException getResponseStatusException(final HttpStatus status, final String reason) {
		return new ResponseStatusException(status, reason);
	}
}
