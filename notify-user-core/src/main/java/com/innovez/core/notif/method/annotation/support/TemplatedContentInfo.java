package com.innovez.core.notif.method.annotation.support;

import java.util.Collections;
import java.util.Map;

import org.springframework.util.Assert;

import com.innovez.core.notif.commons.DefaultParameterizedTextHolder;
import com.innovez.core.notif.commons.ParameterizedTextHolder;

/**
 * 
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public final class TemplatedContentInfo implements ContentInfo {
	private final ParameterizedTextHolder templateHolder;
	private final Map<String, Object> modelParameters;
	
	public TemplatedContentInfo(String templateHolder, Map<String, Object> modelParameters) {
		Assert.hasText(templateHolder, "Template parameter should not be null");
		Assert.notNull(modelParameters, "Parameter map object should not be null");
		
		this.templateHolder = new DefaultParameterizedTextHolder(templateHolder);
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