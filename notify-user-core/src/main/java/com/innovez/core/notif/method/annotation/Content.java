package com.innovez.core.notif.method.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation type for encapsulating content information of any notification.
 * Used on {@link Definition}, annotation type which hold all notification
 * information.
 * 
 * @author zakyalvan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
public @interface Content {
	/**
	 * Template name or content for notification content. Template can contain
	 * one or more placeholder. Each placeholder declared in form of
	 * ${placeholderExpression}, and will be evaluated using SpEL evaluator with
	 * all subject's model variables will be set as evaluation context variables.
	 * 
	 * @return
	 */
	String template();

	/**
	 * Model map used for replacing all placeholder value declared in content
	 * template.
	 * 
	 * @return
	 */
	Model[] models() default {};
}
