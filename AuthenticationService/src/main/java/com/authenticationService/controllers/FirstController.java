package com.authenticationService.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/MyLuck/v1")
public class FirstController {

	@GetMapping("/Welcome")
	public String welcome() {
		return "<h1>Welcome to Spring Boot</h1>";
	}
}