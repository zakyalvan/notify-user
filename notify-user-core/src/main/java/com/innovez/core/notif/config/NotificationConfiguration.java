package com.innovez.core.notif.config;

import java.util.Properties;

import javax.mail.internet.InternetAddress;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.innovez.core.notif.DefaultNotificationManager;
import com.innovez.core.notif.email.EmailDefinitionProcessor;
import com.innovez.core.notif.email.EmailNotificationSender;
import com.innovez.core.notif.method.annotation.support.DefinitionProcessingManager;
import com.innovez.core.notif.method.aspects.PublishNotificationAnnotatedMethodAdvisor;

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
	public static JmsBrokerSupportBeanFactoryPostProcessor beanFactoryPostProcessor() {
		return new JmsBrokerSupportBeanFactoryPostProcessor();
	}
	
	@Bean
	public DefaultNotificationManager notificationManager() {
		DefaultNotificationManager notificationManager = new DefaultNotificationManager();
		return notificationManager;
	}
	
	@Bean
	public PublishNotificationAnnotatedMethodAdvisor publishNotificationAnnotatedAdvisor() {
		PublishNotificationAnnotatedMethodAdvisor advisor = PublishNotificationAnnotatedMethodAdvisor.aspectOf();
		return advisor;
	}
	
	@Bean
	public DefinitionProcessingManager definitionProcessingManager() {
		return new DefinitionProcessingManager();
	}
	@Bean
	public EmailDefinitionProcessor emailDefinitionProcessor() {
		return new EmailDefinitionProcessor();
	}
	
	
	/**
	 * THIS SHOULD CONFIGURED BY USER
	 */
	
	@Bean(name="mailSender")
	public JavaMailSenderImpl javaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.gmail.com");
		javaMailSender.setUsername("josmarinet");
		javaMailSender.setPassword("1nn0vez0ne");
		javaMailSender.setPort(587);
		javaMailSender.setProtocol("smtp");

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.debug", true);
		javaMailProperties.put("mail.smtp.starttls.enable", true);
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailSender.setJavaMailProperties(javaMailProperties);
		
		return javaMailSender;
	}
	
	@Bean
	public EmailNotificationSender emailNotificationSender() throws Exception {
		InternetAddress defaultFromAddress = new InternetAddress("josmarinet@gmail.com", "JOS Notification");
		EmailNotificationSender emailNotificationSender = new EmailNotificationSender("emailNotifSender", javaMailSender(), defaultFromAddress);
		return emailNotificationSender;
	}
}
