package com.example.microservices.users.api.model;


import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequestModel implements Serializable {
  @NotNull
  private String firstName;

  @NotNull
  private String lastName;

  @Email
  private String email;

  @NotNull @Size(min = 3)
  private String password;
}
