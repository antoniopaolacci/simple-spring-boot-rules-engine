package it.sample.rulesengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Many Spring Boot developers always have their main class annotated with @Configuration, 
 * @EnableAutoConfiguration and @ComponentScan. Since these annotations are so frequently 
 * used together (especially if you follow the best practices above), 
 * Spring Boot provides a convenient @SpringBootApplication alternative.
 *  
 * The @SpringBootApplication annotation is equivalent to using 
 * @Configuration, 
 * @EnableAutoConfiguration and 
 * @ComponentScan with their default attributes 
 * 
 */

@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
    	SpringApplication.run(MyApplication.class, args);
    }
   
}
