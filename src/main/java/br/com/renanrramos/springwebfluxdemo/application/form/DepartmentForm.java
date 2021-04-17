package br.com.renanrramos.springwebfluxdemo.application.form;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

@Getter
@Setter
@Builder
@With
public class DepartmentForm {

	@NotBlank
	private String name;

	public DepartmentForm(@NotBlank final String name) {
		this.name = name;
	}

	public DepartmentForm() {

	}

}
