package br.com.renanrramos.springwebfluxdemo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String department;

	public Employee() {

	}

	public Employee(final Integer id, final String name, final String department) {
		this.id = id;
		this.name = name;
		this.department = department;
	}
}
