package br.com.renanrramos.springwebfluxdemo.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MainControllerTest {

	@InjectMocks
	private MainController mainController;

	@Mock
	private RedirectAttributes attributes;

	@Test
	public void redirectToMainPage_shouldRedirectToSwaggerPage() {
		RedirectView redirectedPage = mainController.redirectToMainPage(attributes);
		assertThat(redirectedPage.getUrl(), is("swagger-ui.html"));
	}

}
