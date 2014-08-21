package com.innovez.notif.samples.core.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="innvz_user")
@SuppressWarnings("serial")
public class User implements Serializable {
	@Id
	@NotBlank
	@Column(name="username")
	private String username;
	
	@NotBlank
	@Column(name="full_name")
	private String fullName;
	
	@NotBlank
	@Email
	@Column(name="email_address", unique=true)
	private String emailAddress;
	
	@NotBlank
	@Column(name="password")
	private String password;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="user_role", joinColumns=@JoinColumn(name="username", referencedColumnName="username"), inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="id"))
	private Set<Role> roles = new HashSet<Role>();
	
	@Column(name="account_expired")
	private boolean accountExpired = false;
	
	@Column(name="account_locked")
	private boolean accountLocked = false;
	
	@Column(name="credentials_expired")
	private boolean credentialsExpired = false;
	
	@Column(name="enabled")
	private boolean enabled = true;
	
	public User() {}
	public User(String username, String fullName, String emailAddress, String password) {
		this.username = username;
		this.fullName = fullName;
		this.emailAddress = emailAddress;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public boolean isAccountExpired() {
		return accountExpired;
	}
	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}
	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
