package com.innovez.notif.samples.core.service;

import java.util.Map;

import org.springframework.util.Assert;

/**
 * Default implementation of credential policy, just a simple object.
 * 
 * @author zakyalvan
 */
public class DefaultCredentialPolicy implements CredentialPolicy {
	public static final String MIN_USER_CREDENTIAL_LENGTH_KEY = "minUserCredentialLength";
	public static final String RESET_CREDENTIAL_IMMEDIATELY_KEY = "resetCredentialImmediately";
	public static final String RESET_CREDENTIAL_TICKET_AGE_KEY = "resetCredentialTicketAge";
	public static final String CREDENTIAL_AGE_KEY = "credentialAge";
	
	private final Integer minUserCredentialLength;
	private final boolean resetCredentialImmediately;
	private final Integer resetCredentialTicketAge;
	private final Integer credentialAge;
	
	public DefaultCredentialPolicy(Map<String, Object> policyMap) {
		Assert.notEmpty(policyMap, "Credential policies map should not be null");
		
		minUserCredentialLength = (Integer) policyMap.get(MIN_USER_CREDENTIAL_LENGTH_KEY);
		resetCredentialImmediately = (boolean) policyMap.get(RESET_CREDENTIAL_IMMEDIATELY_KEY);
		resetCredentialTicketAge = (Integer) policyMap.get(RESET_CREDENTIAL_TICKET_AGE_KEY);
		credentialAge = (Integer) policyMap.get(CREDENTIAL_AGE_KEY);
	}
	
	@Override
	public Integer getMinUserCredentialLength() {
		return minUserCredentialLength;
	}

	@Override
	public boolean isResetCredentialImmediately() {
		return resetCredentialImmediately;
	}
	@Override
	public Integer getResetCredentialTicketAge() {
		return resetCredentialTicketAge;
	}
	@Override
	public Integer getCredentialAge() {
		return credentialAge;
	}
}
