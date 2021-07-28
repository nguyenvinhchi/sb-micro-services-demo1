package com.example.microservices.users.repository;

import com.example.microservices.users.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);
}
