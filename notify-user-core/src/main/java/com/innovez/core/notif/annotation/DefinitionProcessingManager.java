package com.innovez.core.notif.annotation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.innovez.core.notif.Notification;

/**
 * 
 * @author zakyalvan
 */
public final class DefinitionProcessingManager {
	@Autowired
	private Set<DefinitionProcessor> translators;
	
	public Collection<DefinitionProcessor> getDefinitionTranslators(Definition definition) {
		Set<DefinitionProcessor> foundTranslators = new HashSet<DefinitionProcessor>();
		for(DefinitionProcessor translator : translators) {
			if(translator.supportsDefinition(definition)) {
				foundTranslators.add(translator);
			}
		}
		return foundTranslators;
	}
	public Collection<Notification> translateDefinition(Definition definition) {
		Set<Notification> notifications = new HashSet<Notification>();
		
		Collection<DefinitionProcessor> definitionTranslators = getDefinitionTranslators(definition);
		for(DefinitionProcessor translator : definitionTranslators) {
			translator.translateDefinition(definition);
		}
		return null;
	}
}
