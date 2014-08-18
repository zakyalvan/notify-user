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
	 * Get content of text container
	 * 
	 * @param parameters
	 * @return
	 */
	String getContent(Map<String, Object> parameters);
}
