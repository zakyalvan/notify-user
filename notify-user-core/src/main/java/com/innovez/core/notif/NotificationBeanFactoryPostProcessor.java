package com.innovez.core.notif;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

/**
 * Post process notification beans, create and register required beans to bean container.
 * 
 * @author zakyalvan
 */
public class NotificationBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationBeanFactoryPostProcessor.class);
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		LOGGER.debug("Post process bean factory.");
		ConnectionFactory connectionFactory = beanFactory.getBean("connectionFactory", ConnectionFactory.class);
		
		Map<String, NotificationSender> notificationSendersMap = beanFactory.getBeansOfType(NotificationSender.class);
		for(String delegatedNotificationSenderName : notificationSendersMap.keySet()) {
			NotificationSender delegatedNotificationSender = notificationSendersMap.get(delegatedNotificationSenderName);
			
			StringBuilder defaultDestinationNameBuilder = new StringBuilder();
			defaultDestinationNameBuilder.append("NOTIF.").append(delegatedNotificationSenderName.toUpperCase());
			
			JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
			jmsTemplate.setDefaultDestinationName(defaultDestinationNameBuilder.toString());
			jmsTemplate.afterPropertiesSet();
			
			JmsBrokerBackedNotificationSenderWrapper notificationSender = new JmsBrokerBackedNotificationSenderWrapper(jmsTemplate, delegatedNotificationSender);
			beanFactory.registerSingleton(delegatedNotificationSenderName.concat("Wrapper"), notificationSender);
			
			MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(delegatedNotificationSender);
			listenerAdapter.setDefaultListenerMethod("sendNotification");
			
			DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
			listenerContainer.setConnectionFactory(connectionFactory);
			listenerContainer.setMaxConcurrentConsumers(1);
			listenerContainer.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
			listenerContainer.setSessionTransacted(true);
			listenerContainer.setAcceptMessagesWhileStopping(true);
			listenerContainer.setMessageListener(listenerAdapter);
			listenerContainer.setDestinationName(defaultDestinationNameBuilder.toString());
			listenerContainer.afterPropertiesSet();
			
			beanFactory.registerSingleton(delegatedNotificationSenderName.concat("ListenerContainer"), listenerContainer);
		}
				
		NotificationManager notificationManager = beanFactory.getBean(DefaultNotificationManager.class);
		Collection<NotificationSender> notificationSenders = new HashSet<NotificationSender>();
		for(NotificationSender notificationSender : beanFactory.getBeansOfType(JmsBrokerBackedNotificationSenderWrapper.class).values()) {
			notificationSenders.add(notificationSender);
		}
		notificationManager.setNotificationSenders(notificationSenders);
	}
}
