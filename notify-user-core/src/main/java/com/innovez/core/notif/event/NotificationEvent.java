package com.innovez.core.notif.event;

import org.springframework.context.ApplicationEvent;

/**
 * Event type to be published when we need to publish notification.
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public abstract class NotificationEvent<T> extends ApplicationEvent {
	public NotificationEvent(T source) {
		super(source);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getSource() {
		return (T) super.getSource();
	}
}
