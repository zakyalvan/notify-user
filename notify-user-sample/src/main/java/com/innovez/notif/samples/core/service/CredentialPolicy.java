package com.innovez.notif.samples.core.service;

public interface CredentialPolicy {
	Integer getMinUserCredentialLength();
	
	/**
	 * Policy item determine whether to reset password on reset request
	 * submitted, or ask user to input their new password on activation.
	 * 
	 * @return
	 */
	boolean isResetCredentialImmediately();

	/**
	 * Retrieve age of {@link ResetCredentialTicket}, used for determining
	 * {@link ResetCredentialTicket#getExpiredDate()} value.
	 * 
	 * @return
	 */
	Integer getResetCredentialTicketAge();

	/**
	 * Retrieve credential age, number returned represent number of days the
	 * credential will be active.
	 * 
	 * @return
	 */
	Integer getCredentialAge();
}
