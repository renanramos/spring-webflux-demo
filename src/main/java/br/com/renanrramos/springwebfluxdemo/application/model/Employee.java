package br.com.renanrramos.springwebfluxdemo.model;

import java.util.UUID;

import javax.persistence.Column;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;

	private String name;

	private String department;

	public Employee() {

	}

	public Employee(final UUID id, final String name, final String department) {
		this.id = id;
		this.name = name;
		this.department = department;
	}
}
