package com.authenticationService.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.authenticationService.DAO.UserRepository;
import com.authenticationService.dtos.UserRoleDtlsDTO;
import com.authenticationService.entities.User;
import com.authenticationService.util.UserStatus;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	ModelMapper mapper; 
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Override
	public UserRoleDtlsDTO createAdmin(UserRoleDtlsDTO userRoleDtlsDTO) {
		// TODO Auto-generated method stub
		userRoleDtlsDTO.setPassword(passwordEncoder.encode(userRoleDtlsDTO.getPassword()));
		userRoleDtlsDTO.setUserStatus(UserStatus.ACTIVE);
		User user = mapper.map(userRoleDtlsDTO, User.class);
		user = userRepo.save(user);
		
		return mapper.map(user, UserRoleDtlsDTO.class);
	}

}
