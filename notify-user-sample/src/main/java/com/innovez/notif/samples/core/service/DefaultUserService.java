package com.innovez.notif.samples.core.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovez.core.notif.Notification;
import com.innovez.core.notif.NotificationException;
import com.innovez.core.notif.annotation.Content;
import com.innovez.core.notif.annotation.Definition;
import com.innovez.core.notif.annotation.Factory;
import com.innovez.core.notif.annotation.Named;
import com.innovez.core.notif.annotation.Parameter;
import com.innovez.core.notif.annotation.PublishNotification;
import com.innovez.core.notif.annotation.Recipient;
import com.innovez.core.notif.annotation.Subject;
import com.innovez.core.notif.email.EmailContent;
import com.innovez.core.notif.email.EmailNotification;
import com.innovez.core.notif.email.EmailRecipient;
import com.innovez.core.notif.email.EmailSubject;
import com.innovez.core.notif.support.NotificationFactory;
import com.innovez.notif.samples.core.entity.User;

@Service
@Transactional(readOnly=true)
public class DefaultUserService implements UserService {
	@Override
	@PublishNotification(name="register-user-notif",
		definitions={
			@Definition(
				selector="true", 
				recipient=@Recipient, 
				subject=@Subject(), 
				content=@Content(template="as")
			)
		},
		factories={
			@Factory(
				selector="#emailAddress.equalsIgnoreCase('zakyalvan@gmail.com')",
				type=UserRegistrationEmailNotificationFactory.class,
				parameters={
					@Parameter(name="name", expression="#username")
				}
			)
		}
	)
	public User registerUser(@Named("username") String username, @Named("password") String password, @Named("emailAddress") String emailAddress) {
		return new User();
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
			EmailSubject subject = new EmailSubject("Wellcome {-name-}", parameters);
			EmailContent content = new EmailContent();
			
			EmailNotification notification = new EmailNotification(recipient, subject, content);
			return notification;
		}
	}
}
