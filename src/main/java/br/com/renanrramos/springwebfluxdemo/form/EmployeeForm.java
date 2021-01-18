package br.com.renanrramos.springwebfluxdemo.form;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeForm {

	@NotBlank
	private String name;

	@NotBlank
	private String department;

	public EmployeeForm() {

	}
}
