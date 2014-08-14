package com.innovez.core.notif;

import java.util.Collection;


/**
 * Contract for notification service.
 * 
 * @author zakyalvan
 */
public interface NotificationManager {
	/**
	 * Send one notification.
	 * 
	 * @param notification
	 */
	void sendNotification(Notification notification);
	
	/**
	 * Bulk send multiple notifications.
	 * 
	 * @param notifications
	 */
	void sendNotifications(Collection<Notification> notifications);
}
