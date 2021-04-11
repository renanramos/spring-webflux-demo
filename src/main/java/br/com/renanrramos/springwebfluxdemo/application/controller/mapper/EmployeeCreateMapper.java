package br.com.renanrramos.springwebfluxdemo.application.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.com.renanrramos.springwebfluxdemo.application.form.EmployeeForm;
import br.com.renanrramos.springwebfluxdemo.application.model.Department;
import br.com.renanrramos.springwebfluxdemo.application.model.Employee;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeCreateMapper {

	EmployeeCreateMapper INSTANCE = Mappers.getMapper(EmployeeCreateMapper.class);

	@Mapping(source = "department", target = "department", qualifiedByName = "mapDepartmentFrom")
	Employee mapEmployeeFrom(final EmployeeForm employeeForm);

	@Named("mapDepartmentFrom")
	default Department mapDepartmentFrom(final String departmentName) {
		return new Department().withName(departmentName);
	}
}
