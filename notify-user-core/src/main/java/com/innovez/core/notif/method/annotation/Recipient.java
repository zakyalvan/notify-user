package com.innovez.core.notif.method.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import com.innovez.core.notif.commons.RecipientDetails;
import com.innovez.core.notif.method.expression.RecipientResolver;

/**
 * Representing recipient of notification.
 * 
 * @author zakyalvan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
public @interface Recipient {
	/**
	 * Name of recipient. SpEL expression could be used here. With
	 * {@link #address()} attribute will be used for creating
	 * {@link RecipientDetails}.
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * Address of recipient. SpEL expression could be used here. With
	 * {@link #name()} attribute will be used for creating
	 * {@link RecipientDetails}.
	 * 
	 * @return
	 */
	String address() default "";

	/**
	 * SpEL expression, could be used in conjunction with
	 * {@link RecipientResolver} to resolve target users. Please note, this
	 * evaluation expression declared here should return type of
	 * {@link RecipientDetails} or {@link Collection} of that type.
	 * 
	 * @return
	 */
	String expression() default "";
}
