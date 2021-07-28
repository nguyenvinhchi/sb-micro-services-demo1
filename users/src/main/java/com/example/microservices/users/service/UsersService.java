package com.example.microservices.users.service;

import com.example.microservices.users.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
  UserDto createUser(UserDto userDetails);
  UserDto getUserDetailsByEmail(String email);
}
