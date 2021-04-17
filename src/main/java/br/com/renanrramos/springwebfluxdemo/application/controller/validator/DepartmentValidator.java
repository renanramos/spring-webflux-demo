package br.com.renanrramos.springwebfluxdemo.application.controller.validator;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import br.com.renanrramos.springwebfluxdemo.application.model.Department;
import br.com.renanrramos.springwebfluxdemo.infra.service.DepartmentService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DepartmentValidator {

	@Autowired
	private DepartmentService departmentService;

	public void validate(final UUID departmentId)
	{
		final Optional<Department> response = getDepartment(departmentId);

		if (!response.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento inválido " + departmentId);
		}
	}

	private Optional<Department> getDepartment(UUID departmentId)
	{
		return departmentService.findById(departmentId).blockOptional();
	}
}
