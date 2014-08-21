package com.innovez.notif.samples.core.service;

import java.util.List;


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
	ResetCredentialTicket createResetTicket(String username);
	
	/**
	 * Ask whether given user has valid {@link ResetCredentialTicket}.
	 * 
	 * @param username
	 * @return
	 */
	boolean hasValidResetTicket(String username);
	
	/**
	 * Retrieve valid {@link ResetCredentialTicket} for given username.
	 * 
	 * @param username
	 * @return
	 */
	ResetCredentialTicket getValidResetTicket(String username);
	
	/**
	 * Retrieve all valid {@link ResetCredentialTicket} form ticket database.
	 * 
	 * @return
	 */
	List<ResetCredentialTicket> getValidResetTicketList();
	
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
