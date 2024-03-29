package br.com.renanrramos.springwebfluxdemo.application.controller.exception;

import java.time.LocalDateTime;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice(basePackages = "br.com.renanrramos.springwebfluxdemo.controller")
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class EmployeeExceptionHandler {

	@ExceptionHandler({ ResponseStatusException.class })
	public ResponseEntity<Object> handleEntityNotFound(final ResponseStatusException ex) {
		return handleResponseEntity(ex.getRawStatusCode(), ex.getReason());
	}

	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return handleResponseEntity(status.value(), ex.getMessage());
	}

	private ResponseEntity<Object> handleResponseEntity(final int status, final String message) {
		return ResponseEntity
				.status(status)
				.contentType(MediaType.APPLICATION_JSON)
				.body("{\"status\":" + status + ","
						+ "\"message\":\"" + message + "\","
						+ "\"timestamp\":\""+ LocalDateTime.now() +"\"}");
	}
}
