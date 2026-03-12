package com.example.demo.controller;

/**
 * UserController
 *
 * @author Paulo Camacho
 * @version 1.0
 * @created 3/11/26 8:06 PM
 * @since 3/11/26
 */
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
}