package com.example.microservices.limitsservice.api;

import com.example.microservices.limitsservice.bean.Limits;
import com.example.microservices.limitsservice.configuration.LimitsConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LimitsController {
  private final LimitsConfiguration configuration;

  @GetMapping("/limits")
  public Limits retrieveLimits() {
    return new Limits(configuration.getMinimum(), configuration.getMaximum());
  }
}
