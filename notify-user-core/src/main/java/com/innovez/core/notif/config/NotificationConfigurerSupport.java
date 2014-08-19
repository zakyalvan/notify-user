package com.innovez.core.notif.config;

import com.innovez.core.notif.method.expression.ContextVariableRegistrar;

/**
 * Extends this type to configure notification support.
 * 
 * @author zakyalvan
 */
public class NotificationConfigurerSupport implements ContextVariableRegistrar {
	@Override
	public void registerEvalutionContextVariables(ContextVariableRegistry registry) {
		// Just NOP method, developer have to override this to add custom variable.
	}
}
