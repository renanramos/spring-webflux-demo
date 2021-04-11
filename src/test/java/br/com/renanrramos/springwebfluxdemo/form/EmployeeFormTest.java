//package br.com.renanrramos.springwebfluxdemo.form;
//
//import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_NAME;
//import static br.com.renanrramos.springwebfluxdemo.common.Constants.EMPLOYEE_DEPARTMENT;
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
//import br.com.renanrramos.springwebfluxdemo.application.form.EmployeeForm;
//
//@ExtendWith(MockitoExtension.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//public class EmployeeFormTest {
//
//	@Test
//	public void constructor_withAllParameters_shouldInstanciateAnEmployeeForm() {
//		EmployeeForm employeeForm = new EmployeeForm(EMPLOYEE_NAME, EMPLOYEE_DEPARTMENT);
//		assertThat(employeeForm.getName(), is(EMPLOYEE_NAME));
//		assertThat(employeeForm.getDepartment(), is(EMPLOYEE_DEPARTMENT));
//	}
//
//	@Test
//	public void constructor_withoutParameters_shouldInstanciateAnEmployeeForm() {
//		EmployeeForm employeeForm = new EmployeeForm();
//		assertThat(employeeForm.getName(), nullValue());
//		assertThat(employeeForm.getDepartment(), nullValue());
//	}
//
//	@Test
//	public void constructor_withoutNameParameter_shouldInstanciateAnEmployeeForm() {
//		EmployeeForm employeeForm = new EmployeeForm(null, EMPLOYEE_DEPARTMENT);
//		assertThat(employeeForm.getName(), nullValue());
//		assertThat(employeeForm.getDepartment(), is(EMPLOYEE_DEPARTMENT));
//	}
//
//	@Test
//	public void constructor_withoutDepartmentParameter_shouldInstanciateAnEmployeeForm() {
//		EmployeeForm employeeForm = new EmployeeForm(EMPLOYEE_NAME, null);
//		assertThat(employeeForm.getName(), is(EMPLOYEE_NAME));
//		assertThat(employeeForm.getDepartment(), nullValue());
//	}
//
//	@Test
//	public void setName_withValidName_shouldSetEmployeeFormName() {
//		EmployeeForm employeeForm = new EmployeeForm();
//		employeeForm.setName(EMPLOYEE_NAME);
//		assertThat(employeeForm.getName(), is(EMPLOYEE_NAME));
//	}
//
//	@Test
//	public void setDepartment_withValidDepartment_shouldSetEmployeeFormDepartment() {
//		EmployeeForm employeeForm = new EmployeeForm();
//		employeeForm.setDepartment(EMPLOYEE_DEPARTMENT);;
//		assertThat(employeeForm.getDepartment(), is(EMPLOYEE_DEPARTMENT));
//	}
//}
