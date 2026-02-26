package com.example.demo.controller;

/**
 * LandingController
 *
 * @author Paulo Camacho
 * @version 1.0
 * @created 2/25/26 9:20 PM
 * @since 2/25/26
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {

  @GetMapping("/")
  public String landing() {
    return "landing";
  }
}