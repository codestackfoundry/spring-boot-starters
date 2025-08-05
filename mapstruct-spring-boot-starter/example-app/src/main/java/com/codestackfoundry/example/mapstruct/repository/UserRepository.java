package com.codestackfoundry.example.mapstruct.repository;

import com.codestackfoundry.example.mapstruct.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}