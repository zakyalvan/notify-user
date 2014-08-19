package com.innovez.core.notif;

import java.io.Serializable;
import java.util.Date;

import com.innovez.core.notif.commons.RecipientDetails;

/**
 * Base contract for notification object.
 * 
 * @author zakyalvan
 */
public interface Notification extends Serializable {
	/**
	 * Retrieve recipient of notification.
	 * 
	 * @return
	 */
	RecipientDetails getRecipient();
	
	/**
	 * Retrieve subject of notification.
	 * 
	 * @return
	 */
	String getSubject();
	
	/**
	 * Retrieve content of notification.
	 * 
	 * @return
	 */
	String getContent();
	
	/**
	 * Retrieve timestamp of notification.
	 * 
	 * @return
	 */
	Date getTimestamp();
}
