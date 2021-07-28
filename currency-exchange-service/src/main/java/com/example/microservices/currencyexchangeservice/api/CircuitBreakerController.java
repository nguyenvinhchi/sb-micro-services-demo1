package com.example.microservices.currencyexchangeservice.api;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class CircuitBreakerController {

  @GetMapping("/sample-api")
//  @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
//  @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
//  @RateLimiter(name = "default")
  @Bulkhead(name = "sample-api")
  public String sampleApi() {
    log.info("Sample api call received");
//    new RestTemplate().getForEntity("http://localhost:8090/dummy-api", null, String.class);
    return "Sample API";
  }

  public String hardcodedResponse(Exception ex) {
    return "fallback-response";
  }
}
