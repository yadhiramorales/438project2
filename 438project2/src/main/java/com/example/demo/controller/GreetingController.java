/**
 * GreetingController
 *
 * @author Paulo Camacho
 * @version 1.0
 * @created 3/4/26 6:54 PM
 * @since 3/4/26
 */
package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  @GetMapping("/greeting")
  public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return "Hello, " + name + "!";
  }

  @GetMapping("/hello")
  public String hello() {
    return "Welcome to the Job Assignment App!";
  }
}