package com.innovez.notif.samples.core.service;

import java.util.Map;

import org.springframework.util.Assert;

/**
 * Default implementation of credential policy, just a simple object.
 * 
 * @author zakyalvan
 */
public class DefaultCredentialPolicy implements CredentialPolicy {
	public static final String ALWAYS_GENERATE_CREDENTIAL_ON_REGISTRATION_KEY = "alwaysGenerateCredentialOnRegistration";
	public static final String CREDENTIAL_EXPIRATION_WARNING_DAY = "credentialExpirationWarningDay";
	public static final String CREDENTIAL_EXPIRATION_WARNING_FREQUENCY = "credentialExpirationWarningFrequency";
	public static final String MIN_USER_CREDENTIAL_LENGTH_KEY = "minUserCredentialLength";
	public static final String RESET_CREDENTIAL_IMMEDIATELY_KEY = "resetCredentialImmediately";
	public static final String RESET_CREDENTIAL_TICKET_AGE_KEY = "resetCredentialTicketAge";
	public static final String CREDENTIAL_AGE_KEY = "credentialAge";
	
	private final boolean alwaysGenerateCredentialOnRegistration;
	private final Integer credentialExpirationWarningDay;
	private final Integer credentialExpirationWarningFrequency;
	private final Integer minUserCredentialLength;
	private final boolean resetCredentialImmediately;
	private final Integer resetCredentialTicketAge;
	private final Integer credentialAge;
	
	public DefaultCredentialPolicy(Map<String, Object> policyMap) {
		Assert.notEmpty(policyMap, "Credential policies map should not be null");
		
		alwaysGenerateCredentialOnRegistration = (boolean) policyMap.get(ALWAYS_GENERATE_CREDENTIAL_ON_REGISTRATION_KEY);
		credentialExpirationWarningDay = (Integer) policyMap.get(CREDENTIAL_EXPIRATION_WARNING_DAY);
		credentialExpirationWarningFrequency = (Integer) policyMap.get(CREDENTIAL_EXPIRATION_WARNING_FREQUENCY);
		minUserCredentialLength = (Integer) policyMap.get(MIN_USER_CREDENTIAL_LENGTH_KEY);
		resetCredentialImmediately = (boolean) policyMap.get(RESET_CREDENTIAL_IMMEDIATELY_KEY);
		resetCredentialTicketAge = (Integer) policyMap.get(RESET_CREDENTIAL_TICKET_AGE_KEY);
		credentialAge = (Integer) policyMap.get(CREDENTIAL_AGE_KEY);
	}
	
	@Override
	public boolean isAlwaysGenerateCredentialOnRegistration() {
		return alwaysGenerateCredentialOnRegistration;
	}
	
	@Override
	public Integer getCredentialExpirationWarningDay() {
		return credentialExpirationWarningDay;
	}

	@Override
	public Integer getCredentialExpirationWarningFrequency() {
		return credentialExpirationWarningFrequency;
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
