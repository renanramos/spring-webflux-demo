package br.com.renanrramos.springwebfluxdemo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Employee {
	
	private Integer id;

	private String name;

	private String department;

	public Employee() {

	}

	public Employee(Integer id, String name, String department) {
		this.id = id;
		this.name = name;
		this.department = department;
	}
}
