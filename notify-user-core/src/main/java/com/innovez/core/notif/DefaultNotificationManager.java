package com.innovez.core.notif;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.util.Assert;

import com.innovez.core.notif.event.GenericNotificationEvent;
import com.innovez.core.notif.event.NotificationEvent;
import com.innovez.core.notif.send.NotificationSender;

/**
 * Default implementation of {@link NotificationManager}.
 * 
 * @author zakyalvan
 */
public class DefaultNotificationManager implements NotificationManager, ApplicationListener<NotificationEvent<?>> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNotificationManager.class);
	
	private Map<String, NotificationSender> mappedNotificationSenders = new HashMap<String, NotificationSender>();
	
	@Override
	public void setNotificationSenders(Collection<NotificationSender> notificationSenders) {
		mappedNotificationSenders.clear();
		for(NotificationSender notificationSender : notificationSenders) {
			mappedNotificationSenders.put(notificationSender.getName(), notificationSender);
		}
	}

	@Override
	public void sendNotification(Notification notification) {
		Assert.notNull(notification, "Notification object parameter should not be null");
		
		LOGGER.debug("Sending notifications, sending process delegated to one of registered notification sender");
		
		Collection<NotificationSender> delegatableSenders = new HashSet<NotificationSender>();
		
		for(NotificationSender notificationSender : mappedNotificationSenders.values()) {
			LOGGER.debug("Check whether notification sender '{}' ({}) can send given notification object", 
					notificationSender.getName(),
					notificationSender.getClass().getName());
			
			if(notificationSender.canSendNotification(notification)) {
				LOGGER.debug("Notification sender '{}' ({}) can send given notification object, add to delegatable senders", 
						notificationSender.getName(),
						notificationSender.getClass().getName());
				delegatableSenders.add(notificationSender);
			}
		}
		
		if(delegatableSenders.size() == 0) {
			LOGGER.error("No notification sender found for sending given notification object {}", notification.toString());
		}
		
		for(NotificationSender notificationSender : delegatableSenders) {
			LOGGER.debug("Send notification {}", notification.toString());
			notificationSender.sendNotification(notification);
		}
	}

	@Override
	public void sendNotifications(Collection<Notification> notifications) {
		Assert.notNull(notifications, "Notifications collection object parameter should not be null");
		for(Notification notification : notifications) {
			sendNotification(notification);
		}
	}

	@Override
	public void onApplicationEvent(NotificationEvent<?> event) {
		LOGGER.debug("Handle application notification event.");
		if(GenericNotificationEvent.class.isAssignableFrom(event.getClass())) {
			sendNotifications(((GenericNotificationEvent)event).getNotifications());
		}
	}
}
