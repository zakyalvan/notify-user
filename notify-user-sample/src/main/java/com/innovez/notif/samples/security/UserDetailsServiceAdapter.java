package com.innovez.notif.samples.security;

import java.util.HashSet;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.innovez.notif.samples.core.entity.User;
import com.innovez.notif.samples.core.service.UserCrudService;

public class UserDetailsServiceAdapter implements UserDetailsService {
	private UserCrudService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(!userService.isRegisteredUser(username)) {
			throw new UsernameNotFoundException(String.format("Given username '%s' not found in user database", username));
		}
		
		User user = userService.getUserDetails(username);
		
		UserDetails userDetails = new UserDetailsAdapter(
				user.getUsername(), 
				user.getPassword(), 
				new HashSet<String>(), 
				!user.isAccountExpired(),
				!user.isAccountLocked(), 
				!user.isCredentialsExpired(), 
				user.isEnabled());
		
		return userDetails;
	}
}
