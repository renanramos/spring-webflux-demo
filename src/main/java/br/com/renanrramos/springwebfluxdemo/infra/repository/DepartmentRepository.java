package br.com.renanrramos.springwebfluxdemo.infra.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.renanrramos.springwebfluxdemo.application.model.Department;

public interface DepartmentRepository extends CrudRepository<Department, UUID> {

}
