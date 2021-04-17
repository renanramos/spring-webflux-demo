package br.com.renanrramos.springwebfluxdemo.infra.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.renanrramos.springwebfluxdemo.application.messages.constants.Messages;
import br.com.renanrramos.springwebfluxdemo.application.model.Department;
import br.com.renanrramos.springwebfluxdemo.infra.repository.DepartmentRepository;
import br.com.renanrramos.springwebfluxdemo.infra.service.DepartmentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	public DepartmentRepository departmentRepository;

	@Override
	public Mono<Department> create(Department department) {
		return Mono.justOrEmpty(departmentRepository.save(department));
	}

	@Override
	public Mono<Department> findById(UUID departmentId) {
		return Mono.justOrEmpty(departmentRepository.findById(departmentId));
	}

	@Override
	public Flux<Department> findAll() {
		return Flux.fromIterable(departmentRepository.findAll());
	}

	@Override
	public void remove(UUID departmentId) {
		findById(departmentId)
		.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.DEPARTMENT_NOT_FOUND)))
		.blockOptional()
		.ifPresent((department) -> departmentRepository.deleteById(departmentId));
	}

	@Override
	public Department update(Department department) {
		return departmentRepository.save(department);
	}
}
