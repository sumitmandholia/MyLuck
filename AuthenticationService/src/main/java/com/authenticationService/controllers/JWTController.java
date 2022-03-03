package com.authenticationService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authenticationService.entities.User;
import com.authenticationService.model.JWTRequest;
import com.authenticationService.model.JWTResponse;
import com.authenticationService.service.MyUserDetailsService;
import com.authenticationService.util.JWTUtil;

@RestController
@CrossOrigin("*")
public class JWTController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JWTUtil jWTUtil;
	
	@PostMapping("/genarateToken")
	public ResponseEntity generateToken(@RequestBody JWTRequest jwtRequest){
		User userDetails = null;
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
			userDetails = myUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
			
		}
		catch(BadCredentialsException ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad Credentials");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			throw new UsernameNotFoundException("Bad Credentials");
			//ResponseEntity.
		}
		
		String token = jWTUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new JWTResponse(token, userDetails.getUsername(), userDetails.getUserId()));
		
	}
}
