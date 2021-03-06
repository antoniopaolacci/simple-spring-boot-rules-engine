package it.sample.rulesengine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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
@Profile({"default", "cloud"})
public class SpringFoxConfig {
	
	@Bean
	public Docket apiDocket() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("it.sample.rulesengine"))
				.paths(PathSelectors.any())
				.build();
		
	}

	private ApiInfo apiInfo() {
		
		return new ApiInfoBuilder()
				.title("simple-rules-engine")
				.description("Simple Rules Engine")
				.termsOfServiceUrl("http://localhost:8080/")
				.contact(new Contact("Ajay S. Koranne", "", "akoranne"))
				.version("1.0")
				.build();
		
	}
	
}
