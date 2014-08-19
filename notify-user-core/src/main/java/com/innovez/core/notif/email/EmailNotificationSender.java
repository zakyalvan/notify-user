package com.innovez.core.notif.email;

import java.util.Date;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.util.Assert;

import com.innovez.core.notif.Notification;
import com.innovez.core.notif.NotificationException;
import com.innovez.core.notif.email.EmailRecipient.Type;
import com.innovez.core.notif.send.NotificationSender;

/**
 * Implementation of {@link NotificationSender} for sending email notification type.
 * 
 * @author zakyalvan
 */
public class EmailNotificationSender implements NotificationSender {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationSender.class);
	private static final String DEFAULT_NAME = EmailNotificationSender.class.getName();

	/**
	 * Mail sender which responsible for sending email notification.
	 * We specifically use java mail sender adapter so that we can send mime type.
	 */
	private JavaMailSender mailSender;
	
	/**
	 * Name of this sender, used for distinguishing each sender.
	 */
	private String name;

	public EmailNotificationSender(JavaMailSender mailSender) {
		this(DEFAULT_NAME, mailSender);
	}
	public EmailNotificationSender(String name, JavaMailSender mailSender) {
		Assert.notNull(name, "Name parameter should not be null");
		Assert.notNull(mailSender, "Mail sender parameter should not be null");
		
		this.name = name;
		this.mailSender = mailSender;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean canSendNotification(Notification notification) {
		Assert.notNull(notification, "Notification paramter should not be null");
		LOGGER.debug("Ask whether can send notification");
		return EmailNotification.class.isAssignableFrom(notification.getClass());
	}

	@Override
	public void sendNotification(final Notification notification) throws NotificationException {
		Assert.notNull(notification, "Notification parameter should not be null");
		if(!canSendNotification(notification)) {
			LOGGER.error("Can't send notification.");
			throw new NotificationException("Can't send notification. Better check whether this notification sender can send given notification before you call this notification");
		}
		
		LOGGER.debug("First prepare email notification message");
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {		
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
				
				InternetAddress fromAddress = new InternetAddress("josmarinet@gmail.com", "JOS Notification");
				messageHelper.setFrom(fromAddress);
				
				EmailRecipient recipient = (EmailRecipient) notification.getRecipient();
				InternetAddress recipientAddress = new InternetAddress(recipient.getAddress(),recipient.getName());
				if(recipient.getType() == Type.TO) {
					messageHelper.setTo(recipientAddress);
				}
				else if(recipient.getType() == Type.CC) {
					messageHelper.setCc(recipientAddress);
				}
				else if(recipient.getType() == Type.BCC){
					messageHelper.setBcc(recipientAddress);
				}
				
				EmailSubject subject = (EmailSubject) notification.getSubject();
				messageHelper.setSubject(subject.getTemplateHolder().getContent(subject.getParameters()));
				
				EmailContent content = (EmailContent) notification.getContent();
				messageHelper.setText(content.getTemplateHolder().getContent(content.getParameters()));
				
				messageHelper.setSentDate(new Date());
			}
		};
		
		LOGGER.debug("Send email notification");
		mailSender.send(messagePreparator);
	}
}
