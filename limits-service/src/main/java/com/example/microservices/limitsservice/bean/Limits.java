package com.example.microservices.limitsservice.bean;

import lombok.Data;

@Data
public class Limits {
  private final int minimum;
  private final int maximum;
}
