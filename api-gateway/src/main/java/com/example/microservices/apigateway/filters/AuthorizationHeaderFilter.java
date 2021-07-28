package com.example.microservices.apigateway.filters;

import com.example.microservices.apigateway.configuration.AppConfig;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
  private final AppConfig appConfig;

  public AuthorizationHeaderFilter(AppConfig appConfig) {
    super(Config.class);
    this.appConfig = appConfig;
  }
  @Override
  public GatewayFilter apply(Config config) {
    return ((exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();

      if (!request.getHeaders().containsKey("Authorization")) {
        return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
      }

      String authorizationHeader = request.getHeaders().get("Authorization").get(0);
      String jwt = authorizationHeader.replace("Bearer", "");

      if (!isJwtValid(jwt)) {
        return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
      }
      return chain.filter(exchange);
    });
  }

  private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(httpStatus);

    return response.setComplete();
  }

  private boolean isJwtValid(String jwt) {
    boolean result = true;
    String subject = Jwts.parser()
        .setSigningKey(appConfig.getTokenSettings().getSecret())
        .parseClaimsJws(jwt)
        .getBody()
        .getSubject();
    if (StringUtils.isEmpty(subject)) {
      result = false;
    }
    return result;
  }

  public static class Config {

  }
}
