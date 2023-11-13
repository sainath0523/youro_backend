package com.youro.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Youro API", version = "1.0", description = "Youro Web Project API's"))
public class YouroWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(YouroWebApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigure()
	{
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("*").allowedHeaders("*");
				WebMvcConfigurer.super.addCorsMappings(registry);
			}
		};
	}

}
