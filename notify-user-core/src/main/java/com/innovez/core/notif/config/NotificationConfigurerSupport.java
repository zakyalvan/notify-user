package com.innovez.core.notif.config;

import com.innovez.core.notif.method.expression.VariableProviderRegistrar;

/**
 * Extends this type to configure notification support.
 * 
 * @author zakyalvan
 */
public class NotificationConfigurerSupport implements VariableProviderRegistrar {
	@Override
	public void registerVariableProviders(VariableProviderRegistry registry) {
		// Just NOP method, developer have to override this to add custom variable.
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return null;
	}
}
