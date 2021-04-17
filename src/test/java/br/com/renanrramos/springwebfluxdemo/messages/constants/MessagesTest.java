package br.com.renanrramos.springwebfluxdemo.messages.constants;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.renanrramos.springwebfluxdemo.application.messages.constants.Messages;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MessagesTest {

	private static final String EMPLOYEE_NOT_FOUND = "Funcionário não encontrado.";
	private static final String INVALID_EMPLOYEE_FORM = "Formulário inválido.";

	@Test
	public void constructor_withoutParameters_shouldInstanciateMessages() {
		Messages messages = new Messages();
		assertThat(messages, notNullValue());
	}

	@Test
	public void employeeNotFound_verifyMessage() {
		assertThat(EMPLOYEE_NOT_FOUND, is(Messages.EMPLOYEE_NOT_FOUND));
	}

	@Test
	public void invalidEmployeeForm_verifyMessage() {
		assertThat(INVALID_EMPLOYEE_FORM, is(Messages.INVALID_FORM));
	}
}
