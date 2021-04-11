package br.com.renanrramos.springwebfluxdemo.exception;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import br.com.renanrramos.springwebfluxdemo.application.exception.EmployeeExceptionHandler;
import br.com.renanrramos.springwebfluxdemo.application.messages.constants.Messages;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeExceptionHandlerTest {

	@InjectMocks
	private EmployeeExceptionHandler employeeExceptionHandler;

	@Mock
	private HttpRequestMethodNotSupportedException mockNotSupportedException;

	@Mock
	private HttpHeaders mockHeaders;

	@Mock
	private WebRequest mockRequest;

	@Test
	public void handleEntityNotFound_withValidParameters_shouldThrowNotFouncException() {
		ResponseStatusException statusNotFound = new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.EMPLOYEE_NOT_FOUND);
		ResponseEntity<Object> response = employeeExceptionHandler.handleEntityNotFound(statusNotFound);

		assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}

	@Test
	public void handleHttpRequestMethodNotSupported_withValidParameters_shouldThrowMethodNotAllowedException() {
		ResponseEntity<Object> response = employeeExceptionHandler
				.handleHttpRequestMethodNotSupported(mockNotSupportedException, mockHeaders, HttpStatus.METHOD_NOT_ALLOWED, mockRequest);

		assertThat(response.getStatusCode(), is(HttpStatus.METHOD_NOT_ALLOWED));
	}
}
