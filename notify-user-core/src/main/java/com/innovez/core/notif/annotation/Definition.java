package com.innovez.core.notif.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.innovez.core.notif.support.NotificationType;

/**
 * Notification configuration.
 * 
 * @author zakyalvan
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Definition {
	/**
	 * Notification type.
	 * 
	 * @return
	 */
	NotificationType type() default NotificationType.EMAIL;

	/**
	 * Guard expression containing exression condition to be evaluated so that
	 * any particular {@link Definition} can be proceed further.
	 * 
	 * @return
	 */
	String guard() default "true";

	/**
	 * Recipients of notification.
	 * 
	 * @return
	 */
	Recipient[] recipient();

	/**
	 * Subject of notification.
	 * 
	 * @return
	 */
	Subject subject();

	/**
	 * Content of notification.
	 * 
	 * @return
	 */
	Content content();
}
