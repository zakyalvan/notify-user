package com.innovez.core.notif.email;

import org.springframework.util.Assert;

import com.innovez.core.notif.Notification.Recipient;

/**
 * Email recipient type.
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public final class EmailRecipient implements Recipient {
	private final String name;
	private final Type type;
	private final String address;

	public EmailRecipient(String name, String address) {
		this(name, Type.TO, address);
	}
	public EmailRecipient(String name, Type type, String address) {
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
