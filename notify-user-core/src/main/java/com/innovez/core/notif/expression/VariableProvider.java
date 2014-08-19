package com.innovez.core.notif.expression;

/**
 * Contract for object providing variable used in annotation expressions. This
 * object intended for used for providing context dependent variable or
 * determined on runtime instead of at initialization process when context
 * configuration loaded, for example authentication object for current logged in
 * user.
 * 
 * @author zakyalvan
 */
public interface VariableProvider {
	/**
	 * Retrieve name of provider.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retrieve variable. Implementations allowed to return {@code null}, which
	 * means, will not be used in evaluation process.
	 * 
	 * @return
	 */
	Object getVariable();
}