package com.innovez.core.notif;

import java.io.Serializable;
import java.util.Collection;

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
	Recipient getRecipient();
	
	/**
	 * Retrieve subject of notification.
	 * 
	 * @return
	 */
	Subject getSubject();
	
	/**
	 * Retrieve content of notification.
	 * 
	 * @return
	 */
	Content getContent();
	
	/**
	 * Retrieve attachments of notification.
	 * 
	 * @return
	 */
	Collection<Attachment> getAttachment();
	
	/**
	 * Contract for notification's recipient.
	 * 
	 * @author zakyalvan
	 */
	public static interface Recipient extends Serializable {
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
	public static interface Subject extends Serializable {
		/**
		 * Retrieve text of notification's subject.
		 * 
		 * @return
		 */
		String getText();
	}
	
	/**
	 * Contract for notification content wrapper. 
	 * Wrapping mechanism allow us to us several notification mechanisms, not only email.
	 * 
	 * @author zakyalvan
	 */
	public static interface Content extends Serializable {
		/**
		 * Retrieve body of notification's content.
		 * 
		 * @return
		 */
		<T> T getBody(Class<?> bodyType);
	}
	
	public static interface Attachment extends Serializable {
		
	}
}
