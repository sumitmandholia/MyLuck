package com.authenticationService.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

	@GetMapping("/Welcome")
	public String welcome() {
		return "<h1>Welcome to Spring Boot</h1>";
	}
}
