package com.innovez.core.notif.method.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

/**
 * Default {@link PublishNotificationFailureHandler}, implements catch all strategy.
 * 
 * @author zakyalvan
 */
public class DefaultPublishNotificationFailureHandler implements PublishNotificationFailureHandler<Exception> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPublishNotificationFailureHandler.class);
	
	private Integer order;
	private Class<Exception> supportType;
	
	public DefaultPublishNotificationFailureHandler(Integer order) {
		if(order == null) {
			order = Ordered.LOWEST_PRECEDENCE;
		}
		this.order = order;
	}
	
	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public boolean supports(Class<Exception> errorType) {
		return supportType.isAssignableFrom(errorType);
	}

	@Override
	public void handleFailure(Exception errorType) {
		LOGGER.debug("Handle error with type {}", errorType.getClass());
	}
}
