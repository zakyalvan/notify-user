package com.innovez.core.notif;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.util.Assert;

/**
 * Wrapper for any {@link NotificationSender} to get asynchronous behavior in sending notification.
 * This behavior gained bay using jms message broker, or exactly ActiveMQ message broker.
 * 
 * @author zakyalvan
 */
public class JmsBrokerBackedNotificationSenderWrapper implements NotificationSender {
	private static final Logger LOGGER = LoggerFactory.getLogger(JmsBrokerBackedNotificationSenderWrapper.class);

	private final JmsTemplate jmsTemplate;
	private final NotificationSender wrappedNotificationSender;
	
	public JmsBrokerBackedNotificationSenderWrapper(JmsTemplate jmsTemplate, NotificationSender wrappedNotificationSender) {
		Assert.notNull(jmsTemplate, "Jms template parameter should not be null");
		Assert.notNull(wrappedNotificationSender, "Wrapped notification sender parameter should not be null");
		
		this.jmsTemplate = jmsTemplate;
		this.wrappedNotificationSender = wrappedNotificationSender;
	}

	@Override
	public String getName() {
		return wrappedNotificationSender.getName();
	}

	@Override
	public boolean canSendNotification(Notification notification) {
		return wrappedNotificationSender.canSendNotification(notification);
	}

	@Override
	public void sendNotification(final Notification notification) throws NotificationException {
		LOGGER.debug("Try to send message {}", notification.toString());
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage(notification);
				message.setJMSCorrelationID(UUID.randomUUID().toString());
				return message;
			}
		});
	}
}
