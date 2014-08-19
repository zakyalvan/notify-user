package com.innovez.core.notif.method.annotation.support;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.util.Assert;

import com.innovez.core.notif.commons.RecipientDetails;

@SuppressWarnings("serial")
public class SimpleDefinitionDetails implements DefinitionDetails {
	private final NotificationType type;
	private final Collection<RecipientDetails> recipients = new HashSet<RecipientDetails>();
	private final SubjectInfo subject;
	private final ContentInfo content;
		
	public SimpleDefinitionDetails(NotificationType type, Collection<RecipientDetails> recipients, SubjectInfo subject, ContentInfo content) {
		Assert.notNull(type, "Type parameter should not be null");
		Assert.notEmpty(recipients, "Recipients parameter should not be null or empty collection");
		Assert.notNull(subject, "Subject parameter should not be null");
		Assert.notNull(content, "Content parameter should not be null");
		
		this.type = type;
		this.recipients.addAll(recipients);
		this.subject = subject;
		this.content = content;
	}

	@Override
	public NotificationType getType() {
		return type;
	}

	@Override
	public Collection<RecipientDetails> getRecipients() {
		return recipients;
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
