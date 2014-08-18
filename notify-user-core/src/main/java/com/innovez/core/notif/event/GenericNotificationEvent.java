package com.innovez.core.notif.event;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.Assert;

import com.innovez.core.notif.Notification;

@SuppressWarnings("serial")
public class GenericNotificationEvent extends NotificationEvent<Object> {
	private final Set<Notification> notifications;
	
	public GenericNotificationEvent(Object source, Collection<Notification> notifications) {
		super(source);
		
		Assert.notEmpty(notifications, "Notification collection parameter should not be null");
		this.notifications = Collections.unmodifiableSet(new HashSet<Notification>(notifications));
	}

	public Set<Notification> getNotifications() {
		return notifications;
	}
}
