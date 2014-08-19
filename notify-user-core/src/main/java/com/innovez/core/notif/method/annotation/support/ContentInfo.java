package com.innovez.core.notif.method.annotation.support;

import java.io.Serializable;
import java.util.Map;

import com.innovez.core.notif.support.ParameterizedTextHolder;

/**
 * Contract for notification content wrapper. Wrapping mechanism allow us to us
 * several notification mechanisms, not only email.
 * 
 * @author zakyalvan
 */
public interface ContentInfo extends Serializable {
	Map<String, Object> getModels();

	ParameterizedTextHolder getTemplateHolder();
}