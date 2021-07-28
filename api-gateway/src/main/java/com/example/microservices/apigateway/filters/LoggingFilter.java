package com.example.microservices.apigateway.filters;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter implements GlobalFilter, Ordered {

  public static final String X_TRACE_ID = "X-TRACE-ID";
  public static final int LEFT_LIMIT = 97;  // letter 'a'
  public static final int RIGHT_LIMIT = 122; // letter 'z'
  public static final int TARGET_STRING_LENGTH = 5;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    String value = generateTraceId();
    ServerHttpRequest request = exchange.getRequest().mutate().headers((httpHeaders) -> {
      httpHeaders.add(X_TRACE_ID, value);
    }).build();

    log.info("Request -> {}, X-TRACE-ID {}", exchange.getRequest().getPath(),
        exchange.getRequest().getHeaders().get(X_TRACE_ID));
    return chain.filter(exchange.mutate().request(request).build());
  }

  private String generateTraceId() {
    Random random = new Random();
    StringBuffer buffer = new StringBuffer(TARGET_STRING_LENGTH);
    for (int i = 0; i < TARGET_STRING_LENGTH; i++) {
      int randomLimitedInt = LEFT_LIMIT + (int)
          (random.nextFloat() * (RIGHT_LIMIT - LEFT_LIMIT + 1));
      buffer.append((char) randomLimitedInt);
    }
    String prefix = buffer.toString();
    return prefix + "-" + System.currentTimeMillis();
  }

  @Override
  public int getOrder() {
    return 1;
  }
}
