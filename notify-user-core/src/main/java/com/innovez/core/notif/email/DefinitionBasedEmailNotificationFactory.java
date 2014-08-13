package com.innovez.core.notif.email;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.innovez.core.notif.Notification;
import com.innovez.core.notif.NotificationException;
import com.innovez.core.notif.support.NotificationType;
import com.innovez.core.notif.support.TypeAwareNotificationFactory;

/**
 * Create email notification based on definition.
 * 
 * @author zakyalvan
 */
public class DefinitionBasedEmailNotificationFactory implements TypeAwareNotificationFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefinitionBasedEmailNotificationFactory.class);

	@Override
	public boolean canCreateNotification(NotificationType type, Map<String, Object> parameters) {
		return type == NotificationType.EMAIL;
	}

	@Override
	public Notification createNotification(NotificationType type, Map<String, Object> parameters) throws NotificationException {
		Assert.isTrue(canCreateNotification(type, parameters), "Cant create notification. Better check canCreateNotification first, before calling this method");
		
		return null;
	}
}
