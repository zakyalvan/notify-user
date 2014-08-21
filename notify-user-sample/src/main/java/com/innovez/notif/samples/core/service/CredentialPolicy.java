package com.innovez.notif.samples.core.service;

/**
 * Contract for security policy.
 * 
 * @author zakyalvan
 */
public interface CredentialPolicy {
	/**
	 * Determine whether username password generation should be enforced on each
	 * user registration.
	 * 
	 * @return
	 */
	boolean isAlwaysGenerateOnRegistration();

	/**
	 * Retrieve when to to start warning users if their credential will be
	 * expired. If set to number equals or lower than zero, disable warning. Or
	 * we can say, the only information about password expiration will be sent
	 * to users.
	 * 
	 * @return
	 */
	Integer getExpirationWarningDays();

	/**
	 * Frequency on warning about credential expiration per day, if warning enabled.
	 * 
	 * @return
	 * @see CredentialPolicy#getExpirationWarningDays()
	 */
	Integer getExpirationWarningFrequencies();
	
	/**
	 * Policy item determine minimum credential's length.
	 * 
	 * @return
	 */
	Integer getMinimumLength();

	/**
	 * Policy item determine whether to reset password immediately on reset request
	 * submitted, or ask user to input their new password on activation.
	 * 
	 * @return
	 */
	boolean isResetImmediatelyOnResetRequest();

	/**
	 * Policy item determine whether to expire credential immediately on reset request
	 * submitted.
	 * 
	 * @return
	 */
	boolean isExpireImmediatelyOnResetRequest();
	
	/**
	 * Retrieve age of {@link ResetCredentialTicket}, used for determining
	 * {@link ResetCredentialTicket#getExpiredDate()} value.
	 * 
	 * @return
	 */
	Integer getResetTicketMaximumAge();

	/**
	 * Retrieve credential age, number returned represent number of days the
	 * credential will be active.
	 * 
	 * @return
	 */
	Integer getMaximumAge();
}
