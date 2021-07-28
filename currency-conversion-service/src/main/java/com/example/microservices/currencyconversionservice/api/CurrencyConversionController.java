package com.example.microservices.currencyconversionservice.api;

import com.example.microservices.currencyconversionservice.model.CurrencyConversion;
import com.example.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;
import java.math.BigDecimal;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class CurrencyConversionController {

  private final Environment env;
  private final CurrencyExchangeProxy currencyExchangeProxy;

//  @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
//  public CurrencyConversion calculate(@PathVariable String from, @PathVariable String to,
//      @PathVariable BigDecimal quantity) {
//
//    HashMap<String, String> uriVariables = new HashMap<>();
//    uriVariables.put("from", from);
//    uriVariables.put("to", to);
//
//    ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate()
//        .getForEntity("http://localhost:8001/currency-exchange/from/{from}/to/{to}",
//            CurrencyConversion.class, uriVariables);
//    CurrencyConversion currencyConversion = responseEntity.getBody();
//    return CurrencyConversion.builder()
//        .id(currencyConversion.getId())
//        .from(from)
//        .to(to)
//        .conversionMultiple(currencyConversion.getConversionMultiple())
//        .quantity(quantity)
//        .totalCalculatedAmount(currencyConversion.getConversionMultiple().multiply(quantity))
//        .environment(env.getProperty("local.server.port"))
//        .build();
//  }

  @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculate2(@PathVariable String from, @PathVariable String to,
      @PathVariable BigDecimal quantity) {

    CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from, to);
    if (currencyConversion == null) {
      throw new RuntimeException("NOT FOUND");
    }
    return CurrencyConversion.builder()
        .id(currencyConversion.getId())
        .from(from)
        .to(to)
        .conversionMultiple(currencyConversion.getConversionMultiple())
        .quantity(quantity)
        .totalCalculatedAmount(currencyConversion.getConversionMultiple().multiply(quantity))
        .environment(env.getProperty("local.server.port"))
        .build();
  }
}
