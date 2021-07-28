package com.example.microservices.users.api.model;


import java.io.Serializable;
import lombok.Data;

@Data
public class CreateUserResponseModel implements Serializable {
  private String firstName;
  private String lastName;
  private String email;
}
