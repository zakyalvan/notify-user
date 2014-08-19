package com.innovez.core.notif.method.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.innovez.core.notif.support.NotificationFactory;

/**
 * Annotation type to be used on notification creation process. Factory class
 * can be resolved from application context if bean name given on annotation's
 * {@link #bean()} attribute
 * 
 * @author zakyalvan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Factory {
	/**
	 * Type of factory to be used on creating notification object.
	 * 
	 * @return
	 */
	Class<? extends NotificationFactory> type();

	/**
	 * SpEL expression to be evaluated, if evaluated to boolean true, publishing
	 * notification can be proceed further.
	 * 
	 * @return
	 */
	String guard() default "true";

	/**
	 * Bean name if the factory should be resolved from application context.
	 * 
	 * @return
	 */
	String bean() default "";

	/**
	 * Parameter declaration to be used in notification factory process.
	 * 
	 * @return
	 */
	Model[] parameters() default {};
}
