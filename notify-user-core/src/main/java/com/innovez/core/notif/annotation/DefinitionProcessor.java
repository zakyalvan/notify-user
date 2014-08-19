package com.innovez.core.notif.annotation;

import com.innovez.core.notif.Notification;

/**
 * Base contract for strategy object which can process each {@link Definition}
 * annotation to {@link Notification}.
 * 
 * @author zakyalvan
 */
public interface DefinitionProcessor {
	/**
	 * Ask whether {@link DefinitionProcessor} can translate given {@link Definition}
	 * 
	 * @param definition
	 * @return
	 */
	boolean supportsDefinition(Definition definition);
	
	/**
	 * Translate given {@link Definition} into notification.
	 * 
	 * @param definition
	 * @return
	 */
	Notification translateDefinition(Definition definition);
}
