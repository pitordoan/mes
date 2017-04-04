package com.cimpoint.mes.server.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticationProviderImpl implements AuthenticationProvider {

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
		AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		//TODO authenticate user against DB
		//if (authentication.getName().equals(authentication.getCredentials()))
		if (authentication.getName().equals("admin") && authentication.getCredentials().equals("Cp010"))
			return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), AUTHORITIES);
		else
			return null;

	}

	public boolean supports(Class<? extends Object> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	class SimpleGrantedAuthority implements GrantedAuthority {
		private static final long serialVersionUID = 1L;
		private String authority;
		
		public SimpleGrantedAuthority(String authority) {
			this.authority = authority;
		}
		
		@Override
		public String getAuthority() {
			return this.authority;
		}
		
	}
}