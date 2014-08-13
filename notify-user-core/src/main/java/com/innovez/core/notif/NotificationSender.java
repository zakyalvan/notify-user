package com.innovez.core.notif;

/**
 * Delegatable strategy type which can send notification.
 * 
 * @author zakyalvan
 */
public interface NotificationSender {
	/**
	 * Name of sender for identification purpose.
	 * 
	 * @return
	 */
	String getName();
	
	/**
	 * Ask whether sender strategy can send given notification.
	 * 
	 * @param notification
	 * @return
	 */
	boolean canSendNotification(Notification notification);
	
	/**
	 * Process send notification.
	 * 
	 * @param notification
	 * @throws NotificationException
	 */
	void sendNotification(Notification notification) throws NotificationException;
}
