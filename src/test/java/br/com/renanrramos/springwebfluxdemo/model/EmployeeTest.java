//package br.com.renanrramos.springwebfluxdemo.model;
//
//import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_DEPARTMENT;
//import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_ID;
//import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_NAME;
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.CoreMatchers.nullValue;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import br.com.renanrramos.springwebfluxdemo.application.model.Department;
//import br.com.renanrramos.springwebfluxdemo.application.model.Employee;
//
//@ExtendWith(MockitoExtension.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//public class EmployeeTest {
//
//	@Test
//	public void constructor_withoutParameters_shouldInstanciateEmployee() {
//		Employee employee = new Employee();
//		assertThat(employee.getId(), nullValue());
//		assertThat(employee.getName(), nullValue());
//		assertThat(employee.getDepartment(), nullValue());
//	}
//
//	@Test
//	public void constructor_withValidParameters_shouldInstanciateEmployee() {
//		Employee employee = new Employee(EMPLOYEE_ID, EMPLOYEE_NAME, new Department().withName(EMPLOYEE_DEPARTMENT));
//
//		assertThat(employee.getId(), is(EMPLOYEE_ID));
//		assertThat(employee.getName(), is(EMPLOYEE_NAME));
//		assertThat(employee.getDepartment().getName(), is(EMPLOYEE_DEPARTMENT));
//	}
//
//	@Test
//	public void builder_withAllProperties_shouldReturnEmployeeInstance() {
//		Employee employee = Employee.builder().id(EMPLOYEE_ID).name(EMPLOYEE_NAME)
//				.department(new Department().withName(EMPLOYEE_DEPARTMENT)).build();
//		assertThat(employee.getId(), is(EMPLOYEE_ID));
//		assertThat(employee.getName(), is(EMPLOYEE_NAME));
//		assertThat(employee.getDepartment().getName(), is(EMPLOYEE_DEPARTMENT));
//	}
//
//	@Test
//	public void builder_withEmployeeId_shouldReturnEmployeeInstance() {
//		Employee employee = Employee.builder().id(EMPLOYEE_ID).build();
//		assertThat(employee.getId(), is(EMPLOYEE_ID));
//		assertThat(employee.getName(), nullValue());
//		assertThat(employee.getDepartment().getName(), nullValue());
//	}
//
//	@Test
//	public void builder_withEmployeeName_shouldReturnEmployeeInstance() {
//		Employee employee = Employee.builder().name(EMPLOYEE_NAME).build();
//		assertThat(employee.getId(), nullValue());
//		assertThat(employee.getName(), is(EMPLOYEE_NAME));
//		assertThat(employee.getDepartment().getName(), nullValue());
//	}
//
//	@Test
//	public void builder_withEmployeeDepartment_shouldReturnEmployeeInstance() {
//		Employee employee = Employee.builder().department(new Department().withName(EMPLOYEE_DEPARTMENT)).build();
//		assertThat(employee.getId(), nullValue());
//		assertThat(employee.getName(), nullValue());
//		assertThat(employee.getDepartment().getName(), is(EMPLOYEE_DEPARTMENT));
//	}
//
//	@Test
//	public void setId_withValidId_shouldSetEmployeeId() {
//		Employee employee = new Employee();
//		employee.setId(EMPLOYEE_ID);
//
//		assertThat(employee.getId(), is(EMPLOYEE_ID));
//	}
//
//	@Test
//	public void setName_withValidName_shouldSetEmployeeName() {
//		Employee employee = new Employee();
//		employee.setName(EMPLOYEE_NAME);
//
//		assertThat(employee.getName(), is(EMPLOYEE_NAME));
//	}
//
//	@Test
//	public void setDepartment_withValidDepartment_shouldSetEmployeeDepartment() {
//		Employee employee = Employee.builder().department(new Department().withName(EMPLOYEE_DEPARTMENT)).build();
//
//		assertThat(employee.getDepartment().getName(), is(EMPLOYEE_DEPARTMENT));
//	}
//
//	@Test
//	public void withId_withValidId_shouldSetEmployeeId() {
//		Employee employee = Employee.builder().id(EMPLOYEE_ID).build();
//
//		assertThat(employee.getId(), is(EMPLOYEE_ID));
//	}
//
//	@Test
//	public void withName_withValidName_shouldSetEmployeeName() {
//		Employee employee = Employee.builder().name(EMPLOYEE_NAME).build();
//
//		assertThat(employee.getName(), is(EMPLOYEE_NAME));
//	}
//
//	@Test
//	public void withDepartment_withValidDepartment_shouldSetEmployeeDepartment() {
//		Employee employee = new Employee();
//		employee.setDepartment(new Department().withName(EMPLOYEE_DEPARTMENT));
//
//		assertThat(employee.getDepartment().getName(), is(EMPLOYEE_DEPARTMENT));
//	}
//
//	@Test
//	public void toString_withValidEmployee_shouldReturnStringWithEmployeeProperties() {
//		Employee employee = new Employee(EMPLOYEE_ID, EMPLOYEE_NAME, new Department().withName(EMPLOYEE_DEPARTMENT));
//
//		assertThat(employee.toString(), equalTo("Employee(id=8dabb3eb-8031-42d2-a135-2d940e437a12, name=employee, department=department)"));
//	}
//}
