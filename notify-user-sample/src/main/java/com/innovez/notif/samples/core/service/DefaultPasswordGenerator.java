package com.innovez.notif.samples.core.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Default password generator which randomly generate string for password.
 * 
 * @author zakyalvan
 */
@Component
public class DefaultPasswordGenerator implements PasswordGenerator {
	@Autowired
	private CredentialPolicyResolver credentialPolicyResolver;
	
	@Override
	public String generatePassword() {
		Integer minUserCredentialLength = credentialPolicyResolver.resolveCredentialPolicy().getMinUserCredentialLength();
		return RandomStringUtils.randomAlphanumeric(minUserCredentialLength);
	}
}
