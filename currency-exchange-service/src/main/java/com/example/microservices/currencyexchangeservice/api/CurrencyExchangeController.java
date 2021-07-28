package com.example.microservices.currencyexchangeservice.api;

import com.example.microservices.currencyexchangeservice.model.CurrencyExchange;
import com.example.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeController {

  private final Environment env;
  private final CurrencyExchangeRepository repository;

  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public CurrencyExchange retrieveExchangeValue(@PathVariable String from,
      @PathVariable String to) {

    log.info("retrieveExchangeValue called with {} to {}", from, to);
    CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to)
        .orElseThrow(() -> new RuntimeException("Not found"));
    return currencyExchange;
//    return CurrencyExchange.builder()
//        .id(1L)
//        .from("USD")
//        .to("IDR")
//        .conversionMultiple(BigDecimal.valueOf(50))
//        .environment(env.getProperty("local.server.port"))
//        .build();
  }
}
