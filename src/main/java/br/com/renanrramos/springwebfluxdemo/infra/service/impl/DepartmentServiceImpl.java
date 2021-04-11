package br.com.renanrramos.springwebfluxdemo.infra.service.impl;

import java.net.URI;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.springwebfluxdemo.application.model.Department;
import br.com.renanrramos.springwebfluxdemo.infra.service.DepartmentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Override
	public Department create(Department e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Department> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(UUID id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Department update(Department e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI buildEmployeeUri(UriComponentsBuilder uriBuilder, UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

}
