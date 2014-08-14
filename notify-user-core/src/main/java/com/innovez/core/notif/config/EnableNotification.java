package com.innovez.core.notif.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * Enable notification feature. Just mark your configuration using this annotation,
 * all default beans will initialized for you to application context.
 * 
 * @author zakyalvan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({NotificationConfiguration.class})
public @interface EnableNotification {
	boolean enableActiveMQ() default true;
}
