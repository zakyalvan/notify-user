package com.innovez.core.notif.method.annotation;

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
	 * Template name or content for notification subject. Template can contain
	 * one or more placeholder. Each placeholder declared on form of
	 * ${placeholderExpression}, and will be evaluated using SpEL evaluator with
	 * all subject's model variable will be set as evaluation context variable.
	 * 
	 * @return
	 */
	String template();

	/**
	 * Model map used for replacing all placeholder value declared in subject template.
	 * 
	 * @return
	 */
	Model[] models() default {};
}
