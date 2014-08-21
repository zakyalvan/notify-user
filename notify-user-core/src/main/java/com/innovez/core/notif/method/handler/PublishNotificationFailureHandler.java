package com.innovez.core.notif.method.handler;

import org.springframework.core.Ordered;

import com.innovez.core.notif.method.annotation.PublishNotification;

/**
 * <p>
 * Handler when failure happen on sending notification after execution of
 * {@link PublishNotification} annotated method. Please note, this handler not
 * intended for handling error on execution of business related rather than
 * error on notification process. Implement {@link Ordered} so that we could
 * properly ordering the handler.
 * 
 * <p>
 * Default implementation of this contract are
 * {@link DefaultPublishNotificationFailureHandler}, which implements catch all
 * strategy.
 * 
 * @author zakyalvan
 */
public interface PublishNotificationFailureHandler<T extends Throwable> extends
		Ordered {
	/**
	 * Ask whether this handler can handle failure for given type.
	 * 
	 * @param errorType
	 * @return
	 */
	boolean supports(Class<T> errorType);

	/**
	 * Handle failure.
	 * 
	 * @param errorType
	 */
	void handleFailure(T error);
}
