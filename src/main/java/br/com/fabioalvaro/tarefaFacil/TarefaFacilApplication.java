package br.com.fabioalvaro.tarefaFacil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
@EnableSwagger2
//@EnableScheduling
public class TarefaFacilApplication {

	public static void main(String[] args) {
		SpringApplication.run(TarefaFacilApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				System.out.println("CORRSSSSS!!!");
				//CORS PERMITIR TODAS AS URLS
				registry.addMapping("/**").allowedOrigins("*");				
				registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
			}
		};
	}
}
