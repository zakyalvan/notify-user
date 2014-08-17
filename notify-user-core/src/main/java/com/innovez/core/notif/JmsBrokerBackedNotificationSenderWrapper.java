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

public class JmsBrokerBackedNotificationSenderWrapper implements NotificationSender {
	private static final Logger LOGGER = LoggerFactory.getLogger(JmsBrokerBackedNotificationSenderWrapper.class);

	private final JmsTemplate jmsTemplate;
	private final NotificationSender wrappedNotificationSender;
	
	public JmsBrokerBackedNotificationSenderWrapper(JmsTemplate jmsTemplate, NotificationSender wrappedNotificationSender) {
		Assert.notNull(jmsTemplate);
		Assert.notNull(wrappedNotificationSender);
		
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
