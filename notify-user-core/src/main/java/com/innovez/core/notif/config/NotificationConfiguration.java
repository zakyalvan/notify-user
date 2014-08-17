package com.innovez.core.notif.config;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;

import com.innovez.core.notif.DefaultNotificationManager;
import com.innovez.core.notif.NotificationBeanFactoryPostProcessor;
import com.innovez.core.notif.aspects.PublishNotificationAnnotatedAdvisor;

/**
 * Basic configuration for notification support.
 * 
 * @author zakyalvan
 */
@Configuration
public class NotificationConfiguration {
	@Bean(initMethod="start", destroyMethod="stop")
	public BrokerService brokerService() throws Exception {
		BrokerService brokerService = new BrokerService();
		brokerService.setBrokerName("NotificationBroker");
		brokerService.setPersistent(true);
		brokerService.setUseShutdownHook(true);
		brokerService.addConnector("tcp://localhost:61616");
		return brokerService;
	}
	
	@Bean
	public CachingConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL("tcp://localhost:61616");
		//activeMQConnectionFactory.setUserName("admin");
		//activeMQConnectionFactory.setPassword("password");
		
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(activeMQConnectionFactory);
		return connectionFactory;
	}
	
	@Bean
	public static NotificationBeanFactoryPostProcessor beanFactoryPostProcessor() {
		return new NotificationBeanFactoryPostProcessor();
	}
	
	@Bean
	public DefaultNotificationManager notificationManager() {
		DefaultNotificationManager notificationManager = new DefaultNotificationManager();
		return notificationManager;
	}
	
	@Bean
	public PublishNotificationAnnotatedAdvisor publishNotificationAnnotatedAdvisor() {
		PublishNotificationAnnotatedAdvisor advisor = PublishNotificationAnnotatedAdvisor.aspectOf();
		return advisor;
	}
}
