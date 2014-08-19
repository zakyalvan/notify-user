package com.innovez.core.notif.email;

import java.util.Date;

import org.springframework.util.Assert;

import com.innovez.core.notif.Notification;

/**
 * Simple implementation of {@link Notification} to be sent using email protocol.
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public final class EmailNotification implements Notification {
	private final String recipient;
	private final String subject;
	private final String content;
	private final Date timestamp;
	
	public EmailNotification(String recipient, String subject, String content) {
		this(recipient, subject, content, new Date());
	}
	public EmailNotification(String recipient, String subject, String content, Date timestamp) {
		Assert.hasText(recipient, "Recipient parameter should not be null or empty text");
		Assert.hasText(subject, "Subject parameter should not be null or empty text");
		Assert.hasText(content, "Content parameter should not be null or empty text");
		Assert.notNull(timestamp, "Timestamp parameter should not be null");
		
		this.recipient = recipient;
		this.subject = subject;
		this.content = content;
		this.timestamp = timestamp;
	}

	@Override
	public String getRecipient() {
		return recipient;
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}
}
