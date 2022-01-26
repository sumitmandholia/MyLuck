package com.authenticationService.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.authenticationService.DAO.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	private com.authenticationService.entities.User user;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		user = userRepo.findByLoginId(username);
		if(username.equals(user.getLoginId())) {
			return new User(user.getLoginId(), 
							passwordEncoder.encode(user.getPassword()), 
							new ArrayList<GrantedAuthority>());
		}
		else {
			throw new UsernameNotFoundException("User Not Found !!!");
		}
			
	}

}
