package com.innovez.core.notif.support;

import java.util.Map;

import com.innovez.core.notif.Notification;
import com.innovez.core.notif.NotificationException;
import com.innovez.core.notif.method.annotation.support.NotificationType;

/**
 * Notification in which do checking for notification type also in the notification creation.
 * 
 * @author zakyalvan
 */
public interface TypeAwareNotificationFactory {
	/**
	 * 
	 * @param type
	 * @param parameters
	 * @return
	 */
	boolean canCreateNotification(NotificationType type, Map<String, Object> parameters);
	
	/**
	 * 
	 * @param type
	 * @param parameters
	 * @return
	 * @throws NotificationException
	 */
	Notification createNotification(NotificationType type, Map<String, Object> parameters) throws NotificationException;
}
