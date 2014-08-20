package com.innovez.notif.samples.core.service;

import java.util.Date;

import com.innovez.notif.samples.core.entity.User;

public interface ResetCredentialTicket {
	/**
	 * Retrieve ticket number for reset ticket.
	 * 
	 * @return
	 */
	String getNumber();

	/**
	 * Retrieve user for requesting password reset.
	 * 
	 * @return
	 */
	User getUser();

	/**
	 * Retrieve timestamp when when {@link ResetCredentialTicket} issued.
	 * 
	 * @return
	 */
	Date getIssuedDate();

	/**
	 * Retrieve timestamp when when {@link ResetCredentialTicket} expired.
	 * 
	 * @return
	 */
	Date getExpiredDate();
	
	/**
	 * Ask whether this ticket still available to use. Used ticket means not
	 * available ticket.
	 * 
	 * @return
	 */
	boolean isAvailable();
}
