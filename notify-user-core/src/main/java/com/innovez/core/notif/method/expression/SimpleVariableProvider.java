package com.innovez.core.notif.method.expression;

import org.springframework.util.Assert;

/**
 * Just a simple provider, providing value once on initialization process.
 * 
 * @author zakyalvan
 */
public final class SimpleVariableProvider implements VariableProvider {
	private final String name;
	private final Object variable;
		
	public SimpleVariableProvider(String name, Object variable) {
		Assert.hasText(name, "Variable name should not be null");
		Assert.notNull(variable, "Variable should not be null");
		
		this.name = name;
		this.variable = variable;
	}
	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public Object getVariable() {
		return variable;
	}
}
