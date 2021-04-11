package br.com.renanrramos.springwebfluxdemo.application.controller.mapper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.renanrramos.springwebfluxdemo.application.form.EmployeeForm;
import br.com.renanrramos.springwebfluxdemo.application.model.Department;
import br.com.renanrramos.springwebfluxdemo.application.model.Employee;
import br.com.renanrramos.springwebfluxdemo.common.CommonUtils;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeCreateMapperTest {

	@Test
	void mapEmployeeFrom_withValidFields_shouldReturnEmployee() {
		EmployeeForm form = CommonUtils.getEmployeeFormInstance();
		Employee employee = EmployeeCreateMapper.INSTANCE.mapEmployeeFrom(form);

		assertThat(employee.getName(), equalTo(form.getName()));
		assertThat(employee.getDepartment().getName(), equalTo(form.getDepartment()));
	}

	@Test
	void mapDepartmentFrom_withValidName_shouldReturnDepartment() {
		final String name = "department";

		final Department department = EmployeeCreateMapper.INSTANCE.mapDepartmentFrom(name);

		assertThat(department.getName(), equalTo(name));
	}

}
