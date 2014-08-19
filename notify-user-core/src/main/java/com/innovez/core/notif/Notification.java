package com.innovez.core.notif;

import java.io.Serializable;
import java.util.Date;

/**
 * Contract for notification object.
 * 
 * @author zakyalvan
 */
public interface Notification extends Serializable {
	/**
	 * Retrieve recipient of notification.
	 * 
	 * @return
	 */
	String getRecipient();
	
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
