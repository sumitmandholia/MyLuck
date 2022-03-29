package com.authenticationService.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authenticationService.DAO.UserRepository;
import com.authenticationService.entities.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByusername(username);

		if(user != null && username.equalsIgnoreCase(user.getUsername())) {
			return user;
		}
		else {
			throw new UsernameNotFoundException("User Not Found !!!");
		}
			
	}

}
