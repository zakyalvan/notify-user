package com.innovez.core.notif.method.annotation.support;

import java.io.Serializable;
import java.util.Map;

import com.innovez.core.notif.commons.ParameterizedTextHolder;

/**
 * Contract for notification's subject holder. Wrapping mechanism allow us to us
 * several notification mechanisms, not only email.
 * 
 * @author zakyalvan
 */
public interface SubjectInfo extends Serializable {
	/**
	 * Models used for replacing template placeholders.
	 * 
	 * @return
	 */
	Map<String, Object> getModels();

	/**
	 * Template holder.
	 * 
	 * @return
	 */
	ParameterizedTextHolder getTemplateHolder();
}