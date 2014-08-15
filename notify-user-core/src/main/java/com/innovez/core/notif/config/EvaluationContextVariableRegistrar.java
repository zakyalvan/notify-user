package com.innovez.core.notif.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.innovez.core.notif.aspects.PublishNotificationAnnotatedAdvisor;

/**
 * Contract for types used in registering evaluation context variable, so that can be used in
 * evaluation of notification parameter.
 * 
 * @author zakyalvan
 */
public interface EvaluationContextVariableRegistrar {
	/**
	 * This will be called on {@link PublishNotificationAnnotatedAdvisor} to get additional evaluation context variable.
	 * 
	 * @param registration
	 */
	void registerEvalutionContextVariables(EvaluationContextVariableRegistry registry);
	
	public static final class EvaluationContextVariableRegistry {
		private Collection<String> reservedNames;
		private Map<String, Object> variableRegistry = new HashMap<String, Object>();
		
		public EvaluationContextVariableRegistry(Collection<String> reservedNames) {
			this.reservedNames = reservedNames;
		}
		
		/**
		 * Add new variable.
		 * 
		 * @param name
		 * @param variable
		 */
		public void add(String name, Object variable) {
			Assert.isTrue(!reservedNames.contains(name), "Given name is reserved name which used internally");
			Assert.isTrue(!contains(name), "Given name already exists in this registry, overriding not allowed, choose another name");
			variableRegistry.put(name, variable);
		}
		
		/**
		 * Ask whether this registry already contains variable with given name.
		 * 
		 * @param name
		 * @return
		 */
		public boolean contains(String name) {
			return variableRegistry.keySet().contains(name);
		}
		
		/**
		 * Remove variable with given name from registry.
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
		 * Retrieve registry variable for given name.
		 * 
		 * @param name
		 * @return
		 */
		public Object get(String name) {
			return variableRegistry.get(name);
		}
		
		/**
		 * Retrieve all variables in registry.
		 * 
		 * @return
		 */
		public Map<String, Object> getAll() {
			return new HashMap<String, Object>(variableRegistry);
		}
	}
}
