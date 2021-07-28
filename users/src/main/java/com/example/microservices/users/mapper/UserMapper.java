package com.example.microservices.users.mapper;

import com.example.microservices.users.api.model.CreateUserRequestModel;
import com.example.microservices.users.api.model.CreateUserResponseModel;
import com.example.microservices.users.dto.UserDto;
import com.example.microservices.users.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper  {

  UserDto createUserReqToUserDto(CreateUserRequestModel user);

  CreateUserResponseModel dtoToCreateUserResponse(UserDto user);

  UserDto entityToUserDto(UserEntity user);

  UserEntity dtoToUserEntity(UserDto user);

}
