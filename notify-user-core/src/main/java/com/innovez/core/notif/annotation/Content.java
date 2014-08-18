package com.innovez.core.notif.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation type for encapsulating content information of any notification.
 * Used on {@link Definition}, annotation type which hold all notification information.
 * 
 * @author zakyalvan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
public @interface Content {
	/**
	 * Template name or content to be used for rendering content.
	 * 
	 * @return
	 */
	String template();
	
	/**
	 * Parameter to used as replacer for placeholder on template.
	 * 
	 * @return
	 */
	Parameter[] parameters() default {};
}
