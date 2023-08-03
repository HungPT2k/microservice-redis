package com.example.authfirebase;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@Configuration
@OpenAPIDefinition
public class AuthFirebaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthFirebaseApplication.class, args);
    }

    //    @Bean
//    public Docket productApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("come.example.authfirebase"))
//                .paths(PathSelectors.ant("/user/*"))
//                .build();
//    }
    @Bean
    public OpenAPI config() {
        return new OpenAPI().info(
                new Info()
                        .title("")
                        .version("")
                        .description("")
        );
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedHeaders("*").allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
            }

        };
    }

}
