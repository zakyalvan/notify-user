package com.innovez.core.notif.email;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.innovez.core.notif.Notification.ContentInfo;
import com.innovez.core.notif.support.DefaultParameterizedTextHolder;
import com.innovez.core.notif.support.ParameterizedTextHolder;

/**
 * 
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public final class EmailContent implements ContentInfo {
	private final ParameterizedTextHolder templateHolder;
	private final Map<String, Object> parameters;
	
	public EmailContent(String template, Map<String, Object> parameters) {
		Assert.hasText(template);
		Assert.notEmpty(parameters);
		
		this.templateHolder = new DefaultParameterizedTextHolder(template);
		this.parameters = Collections.unmodifiableMap(parameters);
	}
	
	@Override
	public Map<String, Object> getParameters() {
		return parameters;
	}

	@Override
	public ParameterizedTextHolder getTemplateHolder() {
		return templateHolder;
	}
}