package com.innovez.core.notif.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import com.innovez.core.notif.ActiveMQBrokerBackedNotificationManager;
import com.innovez.core.notif.NotificationSender;
import com.innovez.core.notif.aspects.PublishNotificationAnnotatedAdvisor;
import com.innovez.core.notif.email.EmailNotificationSender;

/**
 * Basic configuration for notification support.
 * 
 * @author zakyalvan
 */
@Configuration
public class NotificationConfiguration implements ApplicationContextAware {
	private ApplicationContext applicationContext;
	
	@Bean
	public ActiveMQBrokerBackedNotificationManager notificationManager() {
		ActiveMQBrokerBackedNotificationManager notificationService = new ActiveMQBrokerBackedNotificationManager();
		Map<String, NotificationSender> notificationSenders = new HashMap<String, NotificationSender>();
		
		JavaMailSender mailSender = applicationContext.getBean(JavaMailSender.class);
		EmailNotificationSender emailNotificationSender = new EmailNotificationSender(mailSender);
		notificationSenders.put(emailNotificationSender.getName(), emailNotificationSender);
		
		notificationService.setNotificationSenders(notificationSenders);
		
		return notificationService;
	}
	
	@Bean
	public PublishNotificationAnnotatedAdvisor publishNotificationAnnotatedAdvisor() {
		PublishNotificationAnnotatedAdvisor advisor = PublishNotificationAnnotatedAdvisor.aspectOf();
		return advisor;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}	
}
