package com.innovez.core.notif.support;

import java.io.Serializable;
import java.util.Map;

/**
 * Base contract for text containing objects.
 * 
 * @author zakyalvan
 */
public interface ParameterizedTextHolder extends Serializable {
	/**
	 * Retrieve raw content of template.
	 * 
	 * @return
	 */
	String getRawContent();
	
	/**
	 * Get evaluated version of containing template text, models used for
	 * replacing placeholders declared on template.
	 * 
	 * @param models
	 * @return
	 */
	String evaluateContent(Map<String, Object> models);
}
