package com.innovez.core.notif.event;

import java.lang.reflect.Method;

import com.innovez.core.notif.annotation.PublishNotification;

/**
 * Event type to be published after executions of {@link PublishNotification} annotated methods.
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public class MethodInvocationNotificationEvent extends NotificationEvent<Method> {
	public MethodInvocationNotificationEvent(Method source) {
		super(source);
	}
}
