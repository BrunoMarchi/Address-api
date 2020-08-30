package com.example.address.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "geocoding.api")
public class GeocodingApiProperties {
    private String key;
    private String baseUrl;
}
