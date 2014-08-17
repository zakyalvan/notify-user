package com.innovez.core.notif.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import com.innovez.core.notif.DefaultNotificationManager;
import com.innovez.core.notif.JmsBrokerBackedNotificationSenderWrapper;
import com.innovez.core.notif.NotificationManager;
import com.innovez.core.notif.NotificationSender;

/**
 * Post process notification beans, create and register required jms related supporting beans to bean container.
 * 
 * @author zakyalvan
 */
public class JmsBrokerSupportBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(JmsBrokerSupportBeanFactoryPostProcessor.class);
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		LOGGER.debug("Post process bean factory, configure and register required beans to bean container (bean factory)");
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
			listenerContainer.setSessionTransacted(true);
			/**
			 * SESSION_TRANSACTED => a fictional value returned from Session.getAcknowledgeMode if the session is transacted. Cannot be set explicitly.
			 */
			// listenerContainer.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
			listenerContainer.setMaxConcurrentConsumers(1);
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
