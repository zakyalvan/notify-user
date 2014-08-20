package com.innovez.notif.samples.core.service;


public interface UserCredentialService {
	/**
	 * Reset password for given username.
	 * The step internally is :
	 * 1. Generate new random password for user (manually).
	 * 2. Save about activation ticket relating to reset password request (Manually).
	 * 3. Send notification containing information for reseting password.
	 * 4. 
	 * 
	 * @param username
	 */
	ResetCredentialTicket resetUserPassword(String username);
	
	/**
	 * Ask whether given user has valid or available credential ticket.
	 * 
	 * @param username
	 * @return
	 */
	boolean hasResetCredentialTicket(String username);
	
	/**
	 * Retrieve available {@link ResetCredentialTicket}.
	 * 
	 * @param username
	 * @return
	 */
	ResetCredentialTicket getResetCredentialTicket(String username);
	
	/**
	 * Activate user.
	 * 
	 * @param username
	 */
	ResetCredentialTicket activateUser(String username, String ticketNumber);
	
	/**
	 * Update user password.
	 * 
	 * @param username
	 * @param newPassword
	 */
	void updateUserPassword(String username, String oldPassword, String newPassword);
}
