package com.example.microservices.users.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.microservices.users.api.model.CreateUserRequestModel;
import com.example.microservices.users.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserMapperTest {

  UserMapper mapper = Mappers.getMapper(UserMapper.class);

  @Test
  void createUserReqToUserDto() {
    CreateUserRequestModel in = new CreateUserRequestModel();
    in.setEmail("test@a.com");
    in.setFirstName("first");
    in.setLastName("last");
    in.setPassword("password123");

    UserDto userDto = mapper.createUserReqToUserDto(in);
    assertThat(userDto).isNotNull();
    assertThat(userDto.getEmail()).isEqualTo("test@a.com");
  }

  @Test
  void dtoToCreateUserResponse() {
  }

  @Test
  void entityToUserDto() {
  }

  @Test
  void dtoToUserEntity() {
  }
}