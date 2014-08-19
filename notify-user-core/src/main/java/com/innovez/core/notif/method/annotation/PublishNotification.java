package com.innovez.core.notif.method.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to be marked on method on which should send notification. If
 * neither {@link #definitions()} nor {@link #factories()} given, the
 * notification publication will be avoided or no notification will be sent.
 * 
 * @author zakyalvan
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PublishNotification {
	/**
	 * Publish notification execution point name. We can use this name to
	 * disable notification further in configuration or globally, so every time
	 * notification execution point reached, send notification will be avoided.
	 * 
	 * @return
	 */
	String name();

	/**
	 * Array of notification definitions.
	 * 
	 * @return
	 */
	Definition[] definitions() default {};

	/**
	 * Array of notification factories meta-information.
	 * 
	 * @return
	 */
	Factory[] factories() default {};

	/**
	 * Global models of notification. This global models would be replaced by
	 * models in each of definition if same model name found.
	 * 
	 * @return
	 */
	Model[] models() default {};
}
