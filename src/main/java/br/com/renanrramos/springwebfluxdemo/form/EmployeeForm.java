package br.com.renanrramos.springwebfluxdemo.form;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeForm {

	@NotBlank
	private String name;

	@NotBlank
	private String department;

	public EmployeeForm() {
		
	}

	public EmployeeForm(@NotBlank final String name, @NotBlank final String department) {
		this.name = name;
		this.department = department;
	}
}
