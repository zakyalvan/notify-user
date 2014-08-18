package com.innovez.core.notif.email;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import org.springframework.util.Assert;

import com.innovez.core.notif.Notification.SubjectInfo;
import com.innovez.core.notif.support.DefaultParameterizedTextHolder;
import com.innovez.core.notif.support.ParameterizedTextHolder;

/**
 * Wrapper for email subject.
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public final class EmailSubject implements SubjectInfo {
	private final ParameterizedTextHolder templateHolder;
	private final Map<String, Object> parameters;
	
	public EmailSubject(String template, Map<String, Object> parameters) {
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