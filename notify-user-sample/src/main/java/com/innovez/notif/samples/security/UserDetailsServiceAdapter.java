package com.innovez.notif.samples.security;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.innovez.notif.samples.core.entity.Role;
import com.innovez.notif.samples.core.entity.User;
import com.innovez.notif.samples.core.service.UserCrudService;

public class UserDetailsServiceAdapter implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceAdapter.class);
	@Autowired
	private UserCrudService userService;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(!userService.isRegisteredUser(username)) {
			throw new UsernameNotFoundException(String.format("Given username '%s' not found in user database", username));
		}
		
		User user = userService.getUserDetails(username);
		
		Collection<String> authoritieNames = new HashSet<String>();
		for(Role role : user.getRoles()) {
			authoritieNames.add(role.getName());
		}
		
		UserDetails userDetails = new UserDetailsAdapter(
				user.getUsername(), 
				user.getPassword(), 
				authoritieNames, 
				!user.isAccountExpired(),
				!user.isAccountLocked(), 
				!user.isCredentialsExpired(), 
				user.isEnabled());
		
		LOGGER.debug("Loaded user {}", userDetails.toString());
		return userDetails;
	}
}
