package com.example.microservices.apigateway.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class AppConfig {
  @Getter
  @Setter
  private TokenSettings tokenSettings;

  @Data
  public static class TokenSettings {
    private Long expirationInMilliseconds;
    private String secret;
    private String urlPath;
  }
}
