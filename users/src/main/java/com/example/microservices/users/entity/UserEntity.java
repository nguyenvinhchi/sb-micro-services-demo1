package com.example.microservices.users.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class UserEntity {
  @Id @GeneratedValue
  private Long id;

  @Column(unique = true)
  private String userId;

  @Column(unique = true)
  private String email;

  private String firstName;
  private String lastName;
  private String encryptedPassword;
}
