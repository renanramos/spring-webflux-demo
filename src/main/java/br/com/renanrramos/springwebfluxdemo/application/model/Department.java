package br.com.renanrramos.springwebfluxdemo.application.model;

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

	public Department(UUID id, String name) {
		this.id = id;
		this.name = name;
	}

	public Department() {

	}
}
