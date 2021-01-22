package br.com.renanrramos.springwebfluxdemo.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

	private static final String BASE_PACKAGE = "br.com.renanrramos.springwebfluxdemo.controller.rest";

	public static final String AUTHORIZATION_HEADER = "Authorization";

	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

	@Bean
	public Docket device() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.pathMapping("/")
				.forCodeGeneration(true)
				.select()
				.apis(apis())
				.paths(PathSelectors.any())
				.build();
	}

	private Predicate<RequestHandler> apis() {
		return RequestHandlerSelectors.basePackage(BASE_PACKAGE);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Spring API")
				.version("1.0.0")
				.contact(new Contact("Renan R Ramos", "https://github.com/renanramos", "renanrramossi@gmail.com"))
				.build();
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
