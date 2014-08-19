package com.innovez.core.notif.method.expression;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.util.Assert;

import com.innovez.core.notif.method.annotation.Model;
import com.innovez.core.notif.method.aspects.PublishNotificationAnnotatedMethodAdvisor;

/**
 * Contract for types used in registering custom evaluation context variables,
 * so that can be used in evaluation of notification {@link Model}
 * expression.
 * 
 * @author zakyalvan
 */
public interface VariableProviderRegistrar {
	/**
	 * Retrieve namespace of registered variables. Used to avoid collisions
	 * with, for example, method argument names.
	 * 
	 * @return
	 */
	String getNamespace();
	
	/**
	 * This will be called on {@link PublishNotificationAnnotatedMethodAdvisor}
	 * to get additional evaluation context variables for parameter expression
	 * evaluation.
	 * 
	 * @param registry
	 */
	void registerVariableProviders(VariableProviderRegistry registry);
	
	/**
	 * Registry object for {@link VariableProvider}.
	 * 
	 * @author zakyalvan
	 */
	public static final class VariableProviderRegistry {
		private Collection<String> reservedNames;
		private Map<String, VariableProvider> variableRegistry = new HashMap<String, VariableProvider>();
		
		public VariableProviderRegistry(Collection<String> reservedNames) {
			this.reservedNames = reservedNames;
		}
		
		/**
		 * Add new {@link VariableProvider} into registry.
		 * 
		 * @param provider
		 */
		public void add(VariableProvider provider) {
			Assert.notNull(provider, "Variable provider parameter should not be null");
			Assert.isTrue(!reservedNames.contains(provider.getName()), "Given variable using reserved variable name. Using reserved name not allowed");
			Assert.isTrue(!contains(provider.getName()), "Given name already exists in this registry, overriding not allowed, choose another name");
			variableRegistry.put(provider.getName(), provider);
		}
		
		/**
		 * Ask whether this registry already contains {@link VariableProvider} with given name.
		 * 
		 * @param name
		 * @return
		 */
		public boolean contains(String name) {
			return variableRegistry.keySet().contains(name);
		}
		
		/**
		 * Remove {@link VariableProvider} object with given name from registry.
		 * 
		 * @param name
		 * @return
		 */
		public boolean remove(String name) {
			boolean contains = contains(name);
			if(contains) {
				variableRegistry.remove(name);
			}
			return contains;
		}
		
		/**
		 * Retrieve {@link VariableProvider} object with given name from registry.
		 * 
		 * @param name
		 * @return
		 */
		public VariableProvider get(String name) {
			return variableRegistry.get(name);
		}
		
		/**
		 * Retrieve all {@link VariableProvider} objects from registry.
		 * 
		 * @return
		 */
		public Collection<VariableProvider> getAll() {
			return new HashSet<VariableProvider>(variableRegistry.values());
		}
	}
}
