package com.innovez.notif.samples.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
	@Column(name="password")
	private String password;
	
	@NotBlank
	@Email
	@Column(name="email_address")
	private String emailAddress;
	
	public User() {}
	public User(String username, String password, String emailAddress) {
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
