package com.codestackfoundry.example.mapstruct.controller;

import com.codestackfoundry.example.mapstruct.dto.UserDTO;
import com.codestackfoundry.example.mapstruct.entity.User;
import com.codestackfoundry.example.mapstruct.mapper.UserMapper;
import com.codestackfoundry.example.mapstruct.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User saved = userRepository.save(userMapper.toEntity(userDTO));
        UserDTO u = userMapper.toDto(saved);
        log.info("Email : " + u.getEmail() + " and name :" + u.getName() + " and Id: " + u.getId());
        return u;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}