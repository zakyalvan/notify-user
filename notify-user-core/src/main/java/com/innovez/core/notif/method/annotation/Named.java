package com.innovez.core.notif.method.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate method parameter so that we can refer the parameter value in SpEL expression.
 * Remember, this should be used because we can't refer method parameter name on runtime,
 * except we are compiling the code with debug parameter (Erasure mechanism).
 * 
 * @author zakyalvan
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Named {
	/**
	 * Name of parameter to be used on SpEL evaluation context.
	 * 
	 * @return
	 */
	String value();
}
