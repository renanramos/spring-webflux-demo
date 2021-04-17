package br.com.renanrramos.springwebfluxdemo.application.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

@Getter
@Setter
@Builder
@ToString
@Entity
@With
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;

	private String name;

	@ManyToOne(targetEntity = Department.class, fetch = FetchType.EAGER)
	private Department department;

	public Employee() {

	}

	public Employee(final UUID id, final String name, final Department department) {
		this.id = id;
		this.name = name;
		this.department = department;
	}
}
