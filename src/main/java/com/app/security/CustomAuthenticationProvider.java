package com.app.security;


import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		 log.info("authentication username:"+authentication.getName());
		 String username = authentication.getName();
		 String password = (String)authentication.getCredentials();
		 
		 if(username.equals("patartimotius")) {
	            return new UsernamePasswordAuthenticationToken(
	            		username, password, getAuthority());
		 }
		 throw new UsernameNotFoundException("Invalid username or password");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.
				class);

	}
	 private List<SimpleGrantedAuthority> getAuthority() {
	   	  return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
	   	  
	   }
	

}
