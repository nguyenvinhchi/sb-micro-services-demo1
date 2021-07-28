package com.example.microservices.users.service;

import com.example.microservices.users.dto.UserDto;
import com.example.microservices.users.entity.UserEntity;
import com.example.microservices.users.mapper.UserMapper;
import com.example.microservices.users.repository.UsersRepository;
import java.util.Collections;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
  private final UsersRepository usersRepository;
  private final UserMapper userMapper;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDto createUser(UserDto userDetails) {
    UserEntity user = userMapper.dtoToUserEntity(userDetails);
    user.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
    user.setUserId(UUID.randomUUID().toString());
    usersRepository.save(user);
    return userMapper.entityToUserDto(user);
  }

  @Override
  public UserDto getUserDetailsByEmail(String email) {
    UserEntity userEntity = usersRepository.findByEmail(email).orElseThrow(
        () -> new UsernameNotFoundException(email));

    return userMapper.entityToUserDto(userEntity);
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserEntity user = usersRepository.findByEmail(username).orElseThrow(
        () -> new UsernameNotFoundException("username"));
    return new User(user.getEmail(), user.getEncryptedPassword(),
        true, true, true, true,
        Collections.emptyList());
  }
}
