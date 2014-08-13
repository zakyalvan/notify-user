package com.innovez.core.notif.email;

import com.innovez.core.notif.Notification.Content;

/**
 * 
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public final class EmailContent implements Content {
	public EmailContent() {
	}
	
	@Override
	public <T> T getBody(Class<?> bodyType) {
		return null;
	}
}