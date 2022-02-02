package com.authenticationService.service;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		// TODO Auto-generated method stub
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return Optional.of(username);
	}

}
