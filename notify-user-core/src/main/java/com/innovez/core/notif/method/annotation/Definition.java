package com.innovez.core.notif.method.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.innovez.core.notif.method.annotation.support.NotificationType;
import com.innovez.core.notif.method.expression.RecipientResolver;

/**
 * Notification configuration.
 * 
 * @author zakyalvan
 */
@Target({ ElementType.METHOD })
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
	 * Recipient declaration of notification. We can resolve multiple recipient
	 * using expression in this attribute in conjunction with {@link RecipientResolver}.
	 * 
	 * @return
	 */
	Recipient recipient();

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
