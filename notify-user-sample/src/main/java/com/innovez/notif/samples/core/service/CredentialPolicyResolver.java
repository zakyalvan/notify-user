package com.innovez.notif.samples.core.service;

/**
 * Resolver for {@link CredentialPolicy} object. Using resolver so that we can
 * retrieve even context dependent {@link CredentialPolicy}, for example based
 * on current logged in user.
 * 
 * @author zakyalvan
 */
public interface CredentialPolicyResolver {
	/**
	 * Resolve {@link CredentialPolicy}.
	 * 
	 * @return
	 */
	CredentialPolicy resolveCredentialPolicy();
}
