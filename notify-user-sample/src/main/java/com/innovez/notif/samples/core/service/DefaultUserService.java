package com.innovez.notif.samples.core.service;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.innovez.core.notif.Notification;
import com.innovez.core.notif.NotificationException;
import com.innovez.core.notif.email.EmailContent;
import com.innovez.core.notif.email.EmailNotification;
import com.innovez.core.notif.email.EmailRecipient;
import com.innovez.core.notif.email.EmailSubject;
import com.innovez.core.notif.method.annotation.Content;
import com.innovez.core.notif.method.annotation.Definition;
import com.innovez.core.notif.method.annotation.Factory;
import com.innovez.core.notif.method.annotation.Named;
import com.innovez.core.notif.method.annotation.Parameter;
import com.innovez.core.notif.method.annotation.PublishNotification;
import com.innovez.core.notif.method.annotation.Recipient;
import com.innovez.core.notif.method.annotation.Subject;
import com.innovez.core.notif.support.NotificationFactory;
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
				guard="true", 
				recipient=@Recipient, 
				subject=@Subject(template="as"), 
				content=@Content(template="as")
			)
		},
		factories={
			@Factory(
				guard="#emailAddress.equalsIgnoreCase('zakyalvan@gmail.com')",
				type=UserRegistrationEmailNotificationFactory.class,
				parameters={
					@Parameter(name="name", expression="#username")
				}
			)
		}
	)
	@Transactional(propagation=Propagation.REQUIRED)
	public User registerUser(@Named("username") String username, @Named("password") String password, @Named("emailAddress") String emailAddress) {
		User user = new User();
		user = entityManager.merge(user);
		entityManager.flush();
		return user;
	}
	
	@Override
	public void startResetPasswordProcess(String username) {
		
	}

	public static class UserRegistrationEmailNotificationFactory implements NotificationFactory {
		@Override
		public boolean canCreateNotification(Map<String, Object> parameters) {
			return true;
		}

		@Override
		public Notification createNotification(Map<String, Object> parameters) throws NotificationException {
			EmailRecipient recipient = new EmailRecipient("Muhammad Zaky Alvan", "zakyalvan@gmail.com");
			EmailSubject subject = new EmailSubject("Welcome {#name}", parameters);
			EmailContent content = new EmailContent("User registration email notification content for {#name}", parameters);
			
			EmailNotification notification = new EmailNotification(recipient, subject, content);
			return notification;
		}
	}
}
