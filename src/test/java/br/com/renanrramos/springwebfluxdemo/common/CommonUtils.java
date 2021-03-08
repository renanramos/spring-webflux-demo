package br.com.renanrramos.springwebfluxdemo.common;

import br.com.renanrramos.springwebfluxdemo.form.EmployeeForm;
import br.com.renanrramos.springwebfluxdemo.model.Employee;

public class CommonUtils {

	public static Employee getEmployeeInstance() {
		return Employee.builder()
				.id(Constants.EMPLOYEE_ID)
				.name(Constants.EMPLOYEE_NAME)
				.department(Constants.EMPLOYEE_DEPARTMENT)
				.build();
	}

	public static EmployeeForm getEmployeeFormInstance() {
		return EmployeeForm.builder()
				.name(Constants.EMPLOYEE_NAME)
				.department(Constants.EMPLOYEE_DEPARTMENT)
				.build();
	}
}
