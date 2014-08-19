package com.innovez.core.notif;

import java.util.Collection;

import com.innovez.core.notif.send.NotificationSender;

/**
 * Contract for notification service.
 * 
 * @author zakyalvan
 */
public interface NotificationManager {
	/**
	 * Set notification usable senders.
	 * 
	 * @param notificationSenders
	 */
	void setNotificationSenders(Collection<NotificationSender> notificationSenders);
	
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
