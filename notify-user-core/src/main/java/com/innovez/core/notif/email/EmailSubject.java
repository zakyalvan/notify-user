package com.innovez.core.notif.email;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;

import com.innovez.core.notif.Notification.Subject;

/**
 * Wrapper for email subject.
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public final class EmailSubject implements Subject, MessageSourceAware {
	private static final String DEFAULT_PLACEHOLDER_PREFIX = "\\{\\-";
	private static final String DEFAULT_PLACEHOLDER_SUFFIX = "\\-\\}";
	
	/**
	 * This is regular expression prefix.
	 */
	private String placeholderPrefix = DEFAULT_PLACEHOLDER_PREFIX;
	
	/**
	 * This is regular expression suffix.
	 */
	private String placeholderSuffix = DEFAULT_PLACEHOLDER_SUFFIX;
	
	private String template;
	private Map<String, Object> parameters = new HashMap<String, Object>();
	
	private MessageSource messageSource;
	
	public EmailSubject(String template, Map<String, Object> parameters) {
		Assert.notNull(template, "Template parameter should not be null");
		Assert.notNull(parameters, "Template parameters map object should not be null");
		
		this.template = template;
		this.parameters.putAll(parameters);
	}

	@Override
	public String getText() {
		String subjectTemplate = template;
		if(messageSource != null) {
			subjectTemplate = messageSource.getMessage(subjectTemplate, 
					null, 
					subjectTemplate, 
					LocaleContextHolder.getLocale());
		}
		
		for(String paramName : parameters.keySet()) {
			String placeholder = String.format("%s%s%s", placeholderPrefix, paramName, placeholderSuffix);
			subjectTemplate = subjectTemplate.replaceAll(placeholder, parameters.get(paramName).toString());
		}
		
		return subjectTemplate;
	}

	public String getPlaceholderPrefix() {
		return placeholderPrefix;
	}
	public void setPlaceholderPrefix(String placeholderPrefix) {
		this.placeholderPrefix = placeholderPrefix;
	}

	public String getPlaceholderSuffix() {
		return placeholderSuffix;
	}
	public void setPlaceholderSuffix(String placeholderSuffix) {
		this.placeholderSuffix = placeholderSuffix;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}