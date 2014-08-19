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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((variable == null) ? 0 : variable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleVariableProvider other = (SimpleVariableProvider) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}
}
