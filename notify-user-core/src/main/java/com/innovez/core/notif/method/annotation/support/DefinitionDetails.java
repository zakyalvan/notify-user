package com.innovez.core.notif.method.annotation.support;

import java.io.Serializable;
import java.util.Collection;

import com.innovez.core.notif.commons.RecipientDetails;
import com.innovez.core.notif.method.annotation.Definition;

/**
 * Type carrying detail of {@link Definition} annotation.
 * 
 * @author zakyalvan
 */
public interface DefinitionDetails extends Serializable {
	/**
	 * Retrieve type of notification.
	 * 
	 * @return
	 */
	NotificationType getType();
	
	/**
	 * Retrieve recipients of notification.
	 * 
	 * @return
	 */
	Collection<RecipientDetails> getRecipients();
	
	/**
	 * Retrieve subject of notification.
	 * 
	 * @return
	 */
	SubjectInfo getSubject();
	
	/**
	 * Retrieve content of notification.
	 * 
	 * @return
	 */
	ContentInfo getContent();
	
	/**
	 * Retrieve attachments of notification.
	 * 
	 * @return
	 */
	Collection<AttachmentInfo> getAttachment();
	
	/**
	 * Object contract represent attachment for notification.
	 * 
	 * @author zakyalvan
	 */
	public static interface AttachmentInfo extends Serializable {
		
	}
}
