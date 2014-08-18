package com.innovez.core.notif;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.innovez.core.notif.support.ParameterizedTextHolder;

/**
 * Contract for notification object.
 * 
 * @author zakyalvan
 */
public interface Notification extends Serializable {
	/**
	 * Retrieve destination of notification.
	 * 
	 * @return
	 */
	RecipientInfo getRecipient();
	
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
	 * Contract for notification's recipient.
	 * 
	 * @author zakyalvan
	 */
	public static interface RecipientInfo extends Serializable {
		/**
		 * Name of recipient.
		 * 
		 * @return
		 */
		String getName();
	}
	
	/**
	 * Contract for notification's subject holder.
	 * Wrapping mechanism allow us to us several notification mechanisms, not only email.
	 * 
	 * @author zakyalvan
	 */
	public static interface SubjectInfo extends Serializable {
		Map<String, Object> getParameters();
		ParameterizedTextHolder getTemplateHolder();
	}
	
	/**
	 * Contract for notification content wrapper. 
	 * Wrapping mechanism allow us to us several notification mechanisms, not only email.
	 * 
	 * @author zakyalvan
	 */
	public static interface ContentInfo extends Serializable {
		Map<String, Object> getParameters();
		ParameterizedTextHolder getTemplateHolder();
	}
	
	/**
	 * Object contract represent attachment for notification.
	 * 
	 * @author zakyalvan
	 *
	 */
	public static interface AttachmentInfo extends Serializable {
		
	}
}
