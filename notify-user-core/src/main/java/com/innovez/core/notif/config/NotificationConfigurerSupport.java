package com.innovez.core.notif.config;

/**
 * Extends this type to configure notification support.
 * 
 * @author zakyalvan
 */
public class NotificationConfigurerSupport implements EvaluationContextVariableRegistrar {
	@Override
	public void registerEvalutionContextVariables(EvaluationContextVariableRegistry registry) {
		// Just NOP method, developer have to override this to add custom variable.
	}
}
