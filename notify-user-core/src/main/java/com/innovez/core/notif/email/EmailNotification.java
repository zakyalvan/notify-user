package com.innovez.core.notif.email;

import java.util.Collection;

import org.springframework.util.Assert;

import com.innovez.core.notif.Notification;

/**
 * Simple implementation of {@link Notification} to be sent using email protocol.
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public final class EmailNotification implements Notification {
	private final RecipientInfo recipient;
	private final SubjectInfo subject;
	private final ContentInfo content;
	
	public EmailNotification(RecipientInfo recipient, SubjectInfo subject, ContentInfo content) {
		Assert.notNull(recipient, "Recipient parameter should not be null");
		Assert.notNull(subject, "Subject parameter should not be null");
		Assert.notNull(content, "Content parameter should not be null");
		
		this.recipient = recipient;
		this.subject = subject;
		this.content = content;
	}

	@Override
	public RecipientInfo getRecipient() {
		return recipient;
	}
	@Override
	public SubjectInfo getSubject() {
		return subject;
	}
	@Override
	public ContentInfo getContent() {
		return content;
	}

	@Override
	public Collection<AttachmentInfo> getAttachment() {
		return null;
	}
}
