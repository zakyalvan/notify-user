package com.innovez.core.notif.method.expression;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javassist.compiler.ast.Variable;

import org.springframework.util.Assert;

import com.innovez.core.notif.method.aspects.PublishNotificationAnnotatedAdvisor;

/**
 * Contract for types used in registering evaluation context variable, so that can be used in
 * evaluation of notification parameter.
 * 
 * @author zakyalvan
 */
public interface ContextVariableRegistrar {
	/**
	 * This will be called on {@link PublishNotificationAnnotatedAdvisor} to get additional evaluation context variable.
	 * 
	 * @param registry
	 */
	void registerEvalutionContextVariables(ContextVariableRegistry registry);
	
	public static final class ContextVariableRegistry {
		private Collection<String> reservedNames;
		private Map<String, Object> variableRegistry = new HashMap<String, Object>();
		
		public ContextVariableRegistry(Collection<String> reservedNames) {
			this.reservedNames = reservedNames;
		}
		
		public void add(VariableProvider provider) {
			Assert.notNull(provider, "Variable provider parameter should not be null");
			Assert.isTrue(!contains(provider.getName()), "Given name already exists in this registry, overriding not allowed, choose another name");
			variableRegistry.put(provider.getName(), provider);
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
