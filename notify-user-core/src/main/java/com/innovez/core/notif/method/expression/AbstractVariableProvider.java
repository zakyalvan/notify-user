package com.innovez.core.notif.method.expression;

import org.springframework.util.Assert;

public abstract class AbstractVariableProvider implements VariableProvider {
	private final String name;
	
	public AbstractVariableProvider(String name) {
		Assert.hasText(name);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
