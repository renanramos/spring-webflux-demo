package br.com.renanrramos.springwebfluxdemo.application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;

	private String name;

	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	@JsonIgnore
	@Builder.Default
	private List<Employee> employees = new ArrayList<>();

	public Department(final UUID id, final String name, final List<Employee> employees) {
		this.id = id;
		this.name = name;
		this.employees = employees;
	}

	public Department() {

	}
}
