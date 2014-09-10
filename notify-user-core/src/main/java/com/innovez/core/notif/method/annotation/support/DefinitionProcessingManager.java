package com.innovez.core.notif.method.annotation.support;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.innovez.core.notif.Notification;

/**
 * Manager for {@link DefinitionProcessor}
 * 
 * @author zakyalvan
 */
public final class DefinitionProcessingManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefinitionProcessingManager.class);
	
	@Autowired(required=false)
	private Set<DefinitionProcessor> translators = new HashSet<DefinitionProcessor>();
	
	public Collection<DefinitionProcessor> getDefinitionProcessors(DefinitionDetails definition) {
		Set<DefinitionProcessor> foundProcessors = new HashSet<DefinitionProcessor>();
		for(DefinitionProcessor processor : translators) {
			if(processor.supportsDefinition(definition)) {
				foundProcessors.add(processor);
			}
		}
		return foundProcessors;
	}
	public Collection<Notification> processDefinition(DefinitionDetails definition) {
		Collection<DefinitionProcessor> definitionProcessors = getDefinitionProcessors(definition);
		
		Set<Notification> processedNotifications = new HashSet<Notification>();
		LOGGER.debug("Iterate all processor and process given definition details {}", definition.toString());
		for(DefinitionProcessor processor : definitionProcessors) {
			LOGGER.debug("Process with {}", processor.toString());
			processedNotifications.addAll(processor.processDefinition(definition));
		}
		return processedNotifications;
	}
}
