package com.innovez.notif.samples.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="innvz_user")
@SuppressWarnings("serial")
public class User implements Serializable {
	@Id
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email_address")
	private String emailAddress;
}
