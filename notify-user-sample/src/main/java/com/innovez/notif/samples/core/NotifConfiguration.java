package com.innovez.notif.samples.core;

import java.util.Properties;

import javax.mail.internet.InternetAddress;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.innovez.core.notif.config.EnableNotification;
import com.innovez.core.notif.email.EmailNotificationSender;

@Configuration
@EnableNotification
public class NotifConfiguration {
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
