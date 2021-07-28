package com.example.microservices.apigateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MyPostFilter implements GlobalFilter, Ordered {

  static final String X_TRACE_ID = "X-TRACE-ID";

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    return chain.filter(exchange).then(Mono.fromRunnable(() -> {
      log.info("X-TRACE-ID {}-> Response to [{}] [{}], status code [{}]",
          exchange.getRequest().getHeaders().get(X_TRACE_ID),
          exchange.getRequest().getMethod(),
          exchange.getRequest().getPath(),
          exchange.getResponse().getStatusCode());
    }));
  }

  @Override
  public int getOrder() {
    return 5;
  }
}
