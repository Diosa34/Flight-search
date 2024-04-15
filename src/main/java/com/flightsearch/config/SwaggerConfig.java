package com.flightsearch.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${application.openapi.dev-url}")
    private String devUrl;

    @Value("${application.openapi.helios-url}")
    private String heliosUrl;

    @Value("${application.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Сервер разработки");

        Server heliosServer = new Server();
        heliosServer.setUrl(heliosUrl);
        heliosServer.setDescription("Helios");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Основной сервер");

        Info info = new Info()
                .title("Документооборот")
                .version("1.1.0")
                .description("Документация API для лабораторной работы по Бизнес Логике.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, heliosServer, prodServer));
    }

}
