package com.innovez.core.notif.method.annotation.support;

import org.springframework.util.Assert;

/**
 * Email recipient type.
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public final class SimpleRecipientInfo implements RecipientDetails {
	private final String name;
	private final Type type;
	private final String address;

	public SimpleRecipientInfo(String name, String address) {
		this(name, Type.TO, address);
	}
	public SimpleRecipientInfo(String name, Type type, String address) {
		Assert.hasLength(name, "Name parameter should not be null or empty text");
		Assert.notNull(type, "Type parameter should not be null");
		Assert.hasLength(address, "Email address parameter should not be null or empty text");
		
		this.name = name;
		this.type = type;
		this.address = address;
	}

	@Override
	public String getName() {
		return name;
	}
	public Type getType() {
		return type;
	}
	public String getAddress() {
		return address;
	}

	public static enum Type {
		TO, 
		CC, 
		BCC
	}
}
