package com.innovez.core.notif.method.annotation.support;

import java.util.Collections;
import java.util.Map;

import org.springframework.util.Assert;

import com.innovez.core.notif.support.DefaultParameterizedTextHolder;
import com.innovez.core.notif.support.ParameterizedTextHolder;

/**
 * Wrapper for email subject.
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public final class TemplatedSubjectInfo implements SubjectInfo {
	private final ParameterizedTextHolder templateHolder;
	private final Map<String, Object> modelParameters;
	
	public TemplatedSubjectInfo(String template, Map<String, Object> modelParameters) {
		Assert.hasText(template);
		Assert.notEmpty(modelParameters);
		
		this.templateHolder = new DefaultParameterizedTextHolder(template);
		this.modelParameters = Collections.unmodifiableMap(modelParameters);
	}
	
	@Override
	public Map<String, Object> getModels() {
		return modelParameters;
	}
	@Override
	public ParameterizedTextHolder getTemplateHolder() {
		return templateHolder;
	}
}