package br.com.renanrramos.springwebfluxdemo.common;

import java.util.UUID;

import br.com.renanrramos.springwebfluxdemo.application.form.EmployeeForm;
import br.com.renanrramos.springwebfluxdemo.application.model.Department;
import br.com.renanrramos.springwebfluxdemo.application.model.Employee;

public class CommonUtils {

	public static Employee getEmployeeInstance() {
		return new Employee().withId(Constants.EMPLOYEE_ID).withName(Constants.EMPLOYEE_NAME)
				.withDepartment(new Department().withName(Constants.EMPLOYEE_DEPARTMENT));
	}

	public static EmployeeForm getEmployeeFormInstance() {
		return new EmployeeForm().withName(Constants.EMPLOYEE_NAME)
				.withDepartmentId(UUID.fromString(Constants.EMPLOYEE_DEPARTMENT));
	}
}
