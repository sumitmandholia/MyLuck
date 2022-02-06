package com.authenticationService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authenticationService.dtos.UserRoleDtlsDTO;
import com.authenticationService.service.UserService;

@RestController
@RequestMapping(path = "/MyLuck/v1")
public class UserController {
	
	@Autowired
	private UserService userServiceImpl;
	
	@PostMapping("/createAdminUser")
	public ResponseEntity<UserRoleDtlsDTO> createAdmin(@RequestBody UserRoleDtlsDTO userRoleDtlsDTO) {
		
		userRoleDtlsDTO = userServiceImpl.createAdmin(userRoleDtlsDTO);
	
		return ResponseEntity.ok(userRoleDtlsDTO);
	
	}
	
	@GetMapping("/getAdminUser/{userId}")
	public ResponseEntity<UserRoleDtlsDTO> getAdminUser(@PathVariable Long userId){
		UserRoleDtlsDTO userRoleDtlsDTO = userServiceImpl.getAdminUser(userId);
		
		return ResponseEntity.ok(userRoleDtlsDTO);

	}

}
