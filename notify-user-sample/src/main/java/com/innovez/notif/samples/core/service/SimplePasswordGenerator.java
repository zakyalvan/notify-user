package com.innovez.notif.samples.core.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Default password generator to be used, which randomly generate string for
 * password with length depend on security policy configuration.
 * 
 * @author zakyalvan
 */
@Component
public class SimplePasswordGenerator implements PasswordGenerator {
	@Autowired
	private CredentialPolicyResolver credentialPolicyResolver;
	
	@Override
	public String generatePassword() {
		Integer minUserCredentialLength = credentialPolicyResolver.resolveCredentialPolicy().getMinimumLength();
		return RandomStringUtils.randomAlphanumeric(minUserCredentialLength);
	}
}
