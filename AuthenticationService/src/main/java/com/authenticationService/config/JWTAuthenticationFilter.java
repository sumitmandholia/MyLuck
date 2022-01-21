package com.authenticationService.config;

import java.io.IOException;
import java.rmi.server.ExportException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.authenticationService.service.MyUserDetailsService;
import com.authenticationService.util.AuthConstants;
import com.authenticationService.util.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String requestTokenHeader = request.getHeader(AuthConstants.AUTHORIZATION_HEADER);
		
		String username = null;
		String password = null;
		
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			
			String token = requestTokenHeader.substring(7);
			try {
				username = jwtUtil.getUsernameFromToken(token);
			}
			catch (ExpiredJwtException e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new ExportException("JWT Token Expired!!! Please Re-login");
			}
			catch (UsernameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new UsernameNotFoundException("Error Message..");
			}
			
			UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken 
								= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
