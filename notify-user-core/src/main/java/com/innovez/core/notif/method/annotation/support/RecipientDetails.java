package com.innovez.core.notif.method.annotation.support;

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
}