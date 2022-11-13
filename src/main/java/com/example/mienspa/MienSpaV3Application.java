package com.example.mienspa;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MienSpaV3Application {

	public static void main(String[] args) {
		SpringApplication.run(MienSpaV3Application.class, args);
	}

	@Bean
	public Docket api() {                
	    return new Docket(DocumentationType.SWAGGER_2)	    
	      .select()                                       
	      .apis(RequestHandlerSelectors.basePackage("com.example.mienspa"))
	      .paths(PathSelectors.any())                     
	      .build()	    
	      .securityContexts(Arrays.asList(securityContext()))
	  	  .securitySchemes(Arrays.asList(apiKey()))
	      .apiInfo(apiInfo());
	}
	
	private ApiKey apiKey() {
	    return new ApiKey("jwtToken", "Authorization", "header");
	}
	
	private SecurityContext securityContext() { 
	    return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
	} 

	private List<SecurityReference> defaultAuth() { 
	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
	    authorizationScopes[0] = authorizationScope; 
	    return Arrays.asList(new SecurityReference("jwtToken", authorizationScopes)); 
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "My REST API", 
	      "Some custom description of API.", 
	      "API TOS", 
	      "Terms of service", 
	      new Contact("John Doe", "www.example.com", "myeaddress@company.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}
}
