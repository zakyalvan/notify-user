package com.innovez.core.notif.support;

import java.util.Map;

import com.innovez.core.notif.Notification;
import com.innovez.core.notif.NotificationException;

/**
 * Contract for object which can create a notification object.
 * 
 * @author zakyalvan
 */
public interface NotificationFactory {
	/**
	 * Ask whether given {@link NotificationFactory} can create notification for given parameter.
	 * 
	 * @param models
	 * @return
	 */
	boolean canCreateNotification(Map<String, Object> models);
	
	/**
	 * Create notification.
	 * 
	 * @param models
	 * @return
	 * @throws NotificationException
	 */
	Notification createNotification(Map<String, Object> models) throws NotificationException;
}
