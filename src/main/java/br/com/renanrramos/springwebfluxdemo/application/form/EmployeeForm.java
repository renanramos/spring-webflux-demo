package br.com.renanrramos.springwebfluxdemo.application.form;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

@Getter
@Setter
@Builder
@With
public class EmployeeForm {

	@NotBlank
	private String name;

	@NotBlank
	private UUID departmentId;

	public EmployeeForm() {

	}

	public EmployeeForm(@NotBlank final String name, @NotBlank final UUID departmentId) {
		this.name = name;
		this.departmentId = departmentId;
	}
}
