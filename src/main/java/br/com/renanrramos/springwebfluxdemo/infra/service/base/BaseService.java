package br.com.renanrramos.springwebfluxdemo.infra.service.base;

import java.net.URI;
import java.util.UUID;

import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseService<T> {

	T create(final T e);

	Mono<T> findById(final UUID id);

	Flux<T> findAll();

	void remove(final UUID id);

	T update(final T e);

	URI buildEmployeeUri(final UriComponentsBuilder uriBuilder, final UUID id);
}
