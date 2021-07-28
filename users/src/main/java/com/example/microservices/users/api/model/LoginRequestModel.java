package com.example.microservices.users.api.model;

import lombok.Data;

@Data
public class LoginRequestModel {
  private String email;
  private String password;
}
