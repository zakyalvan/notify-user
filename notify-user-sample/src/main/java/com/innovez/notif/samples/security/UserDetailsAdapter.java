package com.innovez.notif.samples.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

@SuppressWarnings("serial")
public class UserDetailsAdapter implements UserDetails {
	private String username;
	private String password;
	private Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;
	
	public UserDetailsAdapter(String username, String password, Collection<String> authoritieNames, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
		Assert.hasText(username);
		Assert.hasText(password);
		Assert.notNull(authoritieNames);
		
		this.username = username;
		this.password = password;
		
		for(String authoritiesName : authoritieNames) {
			authorities.add(new SimpleGrantedAuthority(authoritiesName));
		}
		
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
