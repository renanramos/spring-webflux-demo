package br.com.renanrramos.springwebfluxdemo.swagger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SwaggerConfigTest {

	@InjectMocks
	private SwaggerConfig swaggerConfig;
	
	@Test
	public void device_withDefaultProperties_shouldReturnDocketInstance() throws IOException {
		Docket docket = swaggerConfig.device();
		assertThat(docket.getDocumentationType(), is(DocumentationType.SWAGGER_2));
	}

}
