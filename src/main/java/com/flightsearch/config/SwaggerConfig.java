package com.flightsearch.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"prodMain", "devMain"})
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("Документооборот")
                .version("1.1.0")
                .description("Документация API для лабораторной работы по Бизнес Логике.");

        return new OpenAPI()
                .info(info);
    }

}
