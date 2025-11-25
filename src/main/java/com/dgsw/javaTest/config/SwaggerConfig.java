package com.dgsw.javaTest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("JavaTest REST API")
                        .description("Spring Boot 3.5.5 기반 REST API 문서")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("DGSW")
                                .email("test@example.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("로컬 서버")
                ));
    }
}
