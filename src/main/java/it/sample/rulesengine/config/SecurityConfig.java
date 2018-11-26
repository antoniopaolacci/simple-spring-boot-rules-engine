package it.sample.rulesengine.config;

import it.sample.rulesengine.interceptor.UrlLogInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
 	
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

	@Autowired
	UrlLogInterceptor urlLogInterceptor;

	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
		 	 	
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");

	}

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(
				"/v2/api-docs",
				"/swagger-resources",
				"/swagger-resources/configuration/ui",
				"/swagger-resources/configuration/security")
				.permitAll();
		http.csrf().disable();
	}
	
	
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(urlLogInterceptor);
	}
	
}
