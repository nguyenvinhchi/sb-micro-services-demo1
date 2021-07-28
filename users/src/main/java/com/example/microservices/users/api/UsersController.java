package com.example.microservices.users.api;

import com.example.microservices.users.api.model.CreateUserRequestModel;
import com.example.microservices.users.api.model.CreateUserResponseModel;
import com.example.microservices.users.dto.UserDto;
import com.example.microservices.users.mapper.UserMapper;
import com.example.microservices.users.service.UsersService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

  private final UsersService usersService;
  private final UserMapper userMapper;
  private final Environment environment;

  @GetMapping("/status")
  public String checkStatus() {
    return "Working on port " + environment.getProperty("local.server.port") + ", with token = "
        + environment.getProperty("tokenSettings.secret");
  }

  @PostMapping
  public ResponseEntity<CreateUserResponseModel> create(
      @RequestBody @Valid CreateUserRequestModel userDetails) {
    UserDto userDto = userMapper.createUserReqToUserDto(userDetails);

    UserDto user = usersService.createUser(userDto);
    CreateUserResponseModel result = userMapper.dtoToCreateUserResponse(user);
    return ResponseEntity.created(null).body(result);
  }

}
