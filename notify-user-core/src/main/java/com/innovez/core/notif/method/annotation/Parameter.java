package com.innovez.core.notif.method.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Parameter of notification.
 * 
 * @author zakyalvan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
public @interface Parameter {
	/**
	 * Name of parameter.
	 * 
	 * @return
	 */
	String name();
	
	/**
	 * SpEL expression to be evaluated to get the parameter value.
	 * 
	 * @return
	 */
	String expression();
}
