package com.innovez.core.notif.email;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.innovez.core.notif.Notification;
import com.innovez.core.notif.commons.RecipientDetails;
import com.innovez.core.notif.method.annotation.support.DefinitionDetails;
import com.innovez.core.notif.method.annotation.support.DefinitionProcessor;
import com.innovez.core.notif.method.annotation.support.NotificationType;

public class EmailDefinitionProcessor implements DefinitionProcessor {
	@Override
	public boolean supportsDefinition(DefinitionDetails definition) {
		return definition.getType() == NotificationType.EMAIL;
	}

	@Override
	public Collection<Notification> processDefinition(DefinitionDetails definition) {
		Set<Notification> notifications = new HashSet<Notification>();
		for(RecipientDetails recipient : definition.getRecipients()) {
			notifications.add(new EmailNotification(
					recipient, 
					definition.getSubject().getTemplateHolder().evaluateContent(definition.getSubject().getModels()), 
					definition.getContent().getTemplateHolder().evaluateContent(definition.getContent().getModels()))
			);
		}
		return notifications;
	}
}
