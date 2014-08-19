package com.innovez.core.notif.method.expression;

import org.springframework.util.Assert;

/**
 * Abstract type of {@link VariableProvider}. Developer must inherit this type
 * and implementing {@link #getVariable()} method.
 * 
 * @author zakyalvan
 */
public abstract class AbstractVariableProvider implements VariableProvider {
	private final String name;
	
	public AbstractVariableProvider(String name) {
		Assert.hasText(name, "Name paremter should not be null or empty text");
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
