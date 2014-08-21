package com.innovez.notif.samples.core.service;

import java.util.Map;

import org.springframework.util.Assert;

/**
 * Default implementation of credential policy, just a simple object.
 * 
 * @author zakyalvan
 */
public class DefaultCredentialPolicy implements CredentialPolicy {
	public static final String ALWAYS_GENERATE_ON_REGISTRATION_KEY = "alwaysGenerateOnRegistration";
	public static final String EXPIRATION_WARNING_DAYS_KEY = "expirationWarningDays";
	public static final String EXPIRATION_WARNING_FREQUENCIES_KEY = "expirationWarningFrequencies";
	public static final String MINIMUM_LENGTH_KEY = "minimumLength";
	public static final String RESET_IMMEDIATELY_ON_RESET_REQUEST_KEY = "resetImmediatelyOnResetRequest";
	public static final String EXPIRE_IMMEDIATELY_ON_RESET_REQUEST_KEY = "expireImmediatelyOnResetRequest";
	public static final String RESET_TICKET_MAXIMUM_AGE_KEY = "resetTicketMaximumAge";
	public static final String MAXIMUM_AGE_KEY = "maximumAge";
	
	private final boolean alwaysGenerateOnRegistration;
	private final Integer expirationWarningDays;
	private final Integer expirationWarningFrequencies;
	private final Integer minimumLength;
	private final boolean resetImmediatelyOnResetRequest;
	private final boolean expireImmediatelyOnResetRequest;
	private final Integer resetTicketMaximumAge;
	private final Integer maximumAge;
	
	public DefaultCredentialPolicy(Map<String, Object> policyMap) {
		Assert.notEmpty(policyMap, "Credential policies map should not be null");
		
		alwaysGenerateOnRegistration = (boolean) policyMap.get(ALWAYS_GENERATE_ON_REGISTRATION_KEY);
		expirationWarningDays = (Integer) policyMap.get(EXPIRATION_WARNING_DAYS_KEY);
		expirationWarningFrequencies = (Integer) policyMap.get(EXPIRATION_WARNING_FREQUENCIES_KEY);
		minimumLength = (Integer) policyMap.get(MINIMUM_LENGTH_KEY);
		resetImmediatelyOnResetRequest = (boolean) policyMap.get(RESET_IMMEDIATELY_ON_RESET_REQUEST_KEY);
		expireImmediatelyOnResetRequest = (boolean) policyMap.get(EXPIRE_IMMEDIATELY_ON_RESET_REQUEST_KEY);
		resetTicketMaximumAge = (Integer) policyMap.get(RESET_TICKET_MAXIMUM_AGE_KEY);
		maximumAge = (Integer) policyMap.get(MAXIMUM_AGE_KEY);
	}
	
	@Override
	public boolean isAlwaysGenerateOnRegistration() {
		return alwaysGenerateOnRegistration;
	}
	@Override
	public Integer getExpirationWarningDays() {
		return expirationWarningDays;
	}
	@Override
	public Integer getExpirationWarningFrequencies() {
		return expirationWarningFrequencies;
	}
	@Override
	public Integer getMinimumLength() {
		return minimumLength;
	}
	@Override
	public boolean isResetImmediatelyOnResetRequest() {
		return resetImmediatelyOnResetRequest;
	}
	@Override
	public boolean isExpireImmediatelyOnResetRequest() {
		return expireImmediatelyOnResetRequest;
	}
	@Override
	public Integer getResetTicketMaximumAge() {
		return resetTicketMaximumAge;
	}
	@Override
	public Integer getMaximumAge() {
		return maximumAge;
	}
}
