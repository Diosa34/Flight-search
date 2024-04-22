package com.flightsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.flightsearch.config.properties")
public class FlightSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightSearchApplication.class, args);
    }
}
