package br.com.carrental.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.carrental.control"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, newArrayList(new ResponseMessageBuilder()
						.code(404)
						.message("Not Found")
						.build(), new ResponseMessageBuilder()
						.code(500)
						.message("Internal Server Error")
						.build()))
				.globalResponseMessage(RequestMethod.DELETE, newArrayList(new ResponseMessageBuilder()
						.code(204)
						.message("No Content")
						.build(), new ResponseMessageBuilder()
						.code(500)
						.message("Internal Server Error")
						.build()))
				.globalResponseMessage(RequestMethod.POST, newArrayList(new ResponseMessageBuilder()
						.code(409)
						.message("Conflict")
						.build(), new ResponseMessageBuilder()
						.code(201)
						.message("Created")
						.build(), new ResponseMessageBuilder()
						.code(500)
						.message("Internal Server Error")
						.build()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Car Rental API",
				"API that allows cars avaliability and reservations.",
				"Version 1.0", "Terms of Service",
				new Contact("Micael de Souza Brito",
						"https://github.com/dkrenkel/car-rental",
						"msmikaelis@gmail.com"),
				"License of API",
				"API license url",
				Collections.emptyList());
	}
}
