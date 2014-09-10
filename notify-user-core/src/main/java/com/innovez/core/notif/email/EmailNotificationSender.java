package com.innovez.core.notif.email;

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
import com.innovez.core.notif.commons.RecipientDetails;
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
	 * We specifically use  spring's {@link JavaMailSender} adapter so that we can send mime type.
	 */
	private JavaMailSender mailSender;
	
	/**
	 * Default from address used for all email sent using this notification sender.
	 */
	private InternetAddress defaultFromAddress;
	
	/**
	 * Name of this sender, used for distinguishing each sender.
	 */
	private String name;

	public EmailNotificationSender(JavaMailSender mailSender, InternetAddress defaultFromAddress) {
		this(DEFAULT_NAME, mailSender, defaultFromAddress);
	}
	public EmailNotificationSender(String name, JavaMailSender mailSender, InternetAddress defaultFromAddress) {
		Assert.notNull(name, "Name parameter should not be null");
		Assert.notNull(mailSender, "Mail sender parameter should not be null");
		Assert.notNull(defaultFromAddress, "Default from address parameter should not be null");
		
		this.name = name;
		this.mailSender = mailSender;
		this.defaultFromAddress = defaultFromAddress;
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
		
		LOGGER.debug("Send email notification to {}, with subject {} and content {}", notification.getRecipient(), notification.getSubject(), notification.getContent());
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {		
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
				
				messageHelper.setFrom(defaultFromAddress);
				
				RecipientDetails recipient = notification.getRecipient();
				InternetAddress recipientAddress = new InternetAddress(recipient.getAddress(),recipient.getName());
				messageHelper.setTo(recipientAddress);
				
				messageHelper.setSubject(notification.getSubject());
				messageHelper.setText(notification.getContent(), true);
				messageHelper.setSentDate(notification.getTimestamp());
			}
		};
		
		LOGGER.debug("Send email notification");
		mailSender.send(messagePreparator);
	}
}
