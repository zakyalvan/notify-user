package com.innovez.core.notif.method.annotation.support;

import java.util.Collection;

import com.innovez.core.notif.Notification;

/**
 * Base contract for strategy object which can process each {@link DefinitionDetails}
 * annotation to construct {@link Notification}.
 * 
 * @author zakyalvan
 */
public interface DefinitionProcessor {
	/**
	 * Ask whether {@link DefinitionProcessor} can translate given {@link DefinitionDetails}
	 * 
	 * @param definition
	 * @return
	 */
	boolean supportsDefinition(DefinitionDetails definition);
	
	/**
	 * Process given {@link DefinitionDetails} into notifications.
	 * 
	 * @param definition
	 * @return
	 */
	Collection<Notification> processDefinition(DefinitionDetails definition);
}
