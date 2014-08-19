package com.innovez.core.notif.method.annotation.support;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.innovez.core.notif.method.annotation.Definition;
import com.innovez.core.notif.support.ParameterizedTextHolder;

/**
 * Type carrying detail of {@link Definition} annotation.
 * 
 * @author zakyalvan
 */
public interface DefinitionInfo extends Serializable {
	/**
	 * Retrieve destination of notification.
	 * 
	 * @return
	 */
	RecipientDetails getRecipient();
	
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
