package com.authenticationService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authenticationService.model.JWTRequest;
import com.authenticationService.model.JWTResponse;
import com.authenticationService.service.MyUserDetailsService;
import com.authenticationService.util.JWTUtil;

@RestController
public class JWTController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JWTUtil jWTUtil;
	
	@PostMapping("/genarateToken")
	public ResponseEntity generateToken(@RequestBody JWTRequest jwtRequest){
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
			
		}
		catch(Exception ex) {
			throw new UsernameNotFoundException("Bad Credentials");
		}
		
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = jWTUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new JWTResponse(token));
		
	}
}
