package com.innovez.core.notif.commons;

import java.io.Serializable;

/**
 * Contract for notification's recipient.
 * 
 * @author zakyalvan
 */
public interface RecipientDetails extends Serializable {
	/**
	 * Name of recipient.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Address of recipient, this should be used as generic address.
	 * 
	 * @return
	 */
	String getAddress();
}