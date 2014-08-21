package com.innovez.notif.samples.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.innovez.notif.samples.core.service.ResetCredentialTicket;

@Entity
@Table(name="innvz_reset_password_ticket")
public class UserResetCredentialTicket implements ResetCredentialTicket {
	@Id
	@NotEmpty
	private String number;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username")
	private User user;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="issued_date")
	private Date issuedDate;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expired_date")
	private Date expiredDate;
	
	@Column(name="available")
	private boolean active = false;

	@Override
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Date getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	@Override
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	@Override
	public boolean isActive() {
		return active;
	}
	public void setAvailable(boolean active) {
		this.active = active;
	}
}
