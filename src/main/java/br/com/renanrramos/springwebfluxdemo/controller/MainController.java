package br.com.renanrramos.springwebfluxdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class MainController {

	@GetMapping("/")
	public RedirectView redirectToMainPage(RedirectAttributes attributes) {
		RedirectView redirectedPage = new RedirectView();
		redirectedPage.setUrl("swagger-ui.html");
		redirectedPage.setStatusCode(HttpStatus.OK);
		return redirectedPage;
	}
}
