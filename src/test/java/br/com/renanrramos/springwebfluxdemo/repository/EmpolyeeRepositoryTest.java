package br.com.renanrramos.springwebfluxdemo.repository;

import java.util.List;
import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_ID;
import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_NAME;
import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_DEPARTMENT;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.renanrramos.springwebfluxdemo.common.CommonUtils;
import br.com.renanrramos.springwebfluxdemo.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EmpolyeeRepositoryTest {

	private static final int INVALID_EMPLOYEE_ID = 12;

	@InjectMocks
	private EmployeeRepository employeeRepository;

	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(employeeRepository, "initialEmployees", 9);
		employeeRepository.createEmployeesList();
	}

	@Test
	public void createEmployeesList_withValidInitialValue_shouldCreateAListOfEmployees() {
		Flux<List<Employee>> employees = employeeRepository.findAll();
		assertThat(employees.blockFirst().size(), is(10));
	}

	@Test
	public void save_withNewEmployee_shouldSaveEmployee() {
	
		Employee employee = employeeRepository.save(CommonUtils.getEmployeeInstance());
		Flux<List<Employee>> employeesList = employeeRepository.findAll();

		assertThat(employeesList.blockFirst().size(), is(11));
		assertThat(employee.getId(), is(10));
		assertThat(employee.getName(), is(EMPLOYEE_NAME));
		assertThat(employee.getDepartment(), is(EMPLOYEE_DEPARTMENT));
	}

	@Test
	public void findById_withValidEmployeeId_shouldReturnEmployee() {
		Mono<Employee> employeeFound = employeeRepository.findById(EMPLOYEE_ID);

		assertThat(employeeFound.block().getId(), is(EMPLOYEE_ID));
		assertThat(employeeFound.block().getName(), is("Empl 1"));
		assertThat(employeeFound.block().getDepartment(), is("Department 1"));
	}

	@Test
	public void findById_withInvalidEmployeeId_shouldReturnEmpty() {

		Mono<Employee> employeeNotFound = employeeRepository.findById(INVALID_EMPLOYEE_ID);

		assertThat(employeeNotFound.blockOptional().isPresent(), is(false));
	}

	@Test
	public void findAll_withInitializedEmployeesList_shouldReturnList() {
		Flux<List<Employee>> employeesList = employeeRepository.findAll();

		assertThat(employeesList.blockFirst().size(), is(10));
	}

	@Test
	public void removeEmployee_withValidEmployeeId_shouldRemoveSuccessfully() {
		employeeRepository.removeEmployee(EMPLOYEE_ID);
		Flux<List<Employee>> employees = employeeRepository.findAll();

		assertThat(employees.blockFirst().size(), is(9));
	}
}
