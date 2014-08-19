package com.innovez.notif.samples.core.service;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.innovez.core.notif.Notification;
import com.innovez.core.notif.NotificationException;
import com.innovez.core.notif.NotificationFactory;
import com.innovez.core.notif.commons.DefaultParameterizedTextHolder;
import com.innovez.core.notif.commons.ParameterizedTextHolder;
import com.innovez.core.notif.commons.SimpleRecipientDetails;
import com.innovez.core.notif.email.EmailNotification;
import com.innovez.core.notif.method.annotation.Content;
import com.innovez.core.notif.method.annotation.Definition;
import com.innovez.core.notif.method.annotation.Factory;
import com.innovez.core.notif.method.annotation.Model;
import com.innovez.core.notif.method.annotation.Named;
import com.innovez.core.notif.method.annotation.PublishNotification;
import com.innovez.core.notif.method.annotation.Recipient;
import com.innovez.core.notif.method.annotation.Subject;
import com.innovez.notif.samples.core.entity.User;

@Service
@Transactional(readOnly=true)
public class DefaultUserService implements UserService {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@PublishNotification(name="register-user-notif",
		definitions={
			@Definition(
				guard="#emailAddress.equalsIgnoreCase('zakyalvan@gmail.com')", 
				recipient=@Recipient(name="#username", address="#emailAddress"), 
				subject=@Subject(template="This is email subject for {#recipient.name}"), 
				content=@Content(template="This is email content for user with username {#recipient.name} and email address {#recipient.address}")
			)
		},
		factories={
			@Factory(
				guard="#emailAddress.equalsIgnoreCase('zakyalvan@gmail.com')",
				type=UserRegistrationEmailNotificationFactory.class,
				models={
					@Model(name="name", expression="#username")
				}
			)
		},
		models={@Model(name="name", expression="#username"), @Model(name="email", expression="#emailAddress")}
	)
	@Transactional(propagation=Propagation.REQUIRED)
	public User registerUser(@Named("username") String username, @Named("password") String password, @Named("emailAddress") String emailAddress) {
		User user = new User();
		//user = entityManager.merge(user);
		//entityManager.flush();
		return user;
	}
	
	@Override
	public void startResetPasswordProcess(String username) {
		
	}

	public static class UserRegistrationEmailNotificationFactory implements NotificationFactory {
		@Override
		public boolean canCreateNotification(Map<String, Object> models) {
			return true;
		}

		@Override
		public Notification createNotification(Map<String, Object> models) throws NotificationException {
			ParameterizedTextHolder subjectHolder = new DefaultParameterizedTextHolder("Welcome {#name}");
			ParameterizedTextHolder contentHolder = new DefaultParameterizedTextHolder("User registration email notification content for {#name}");
			
			EmailNotification notification = new EmailNotification(new SimpleRecipientDetails("Muhammad Zaky Alvan", "zakyalvan@gmail.com"), subjectHolder.evaluateContent(models), contentHolder.evaluateContent(models));
			return notification;
		}
	}
}
