package com.innovez.core.notif.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
public @interface Subject {
	/**
	 * Template name or content for notification subject.
	 * 
	 * @return
	 */
	String template();
	
	/**
	 * Parameters to for replacing value in template.
	 * 
	 * @return
	 */
	Parameter[] parameters() default {};
}
