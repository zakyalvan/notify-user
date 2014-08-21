package com.innovez.notif.samples.core.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultCredentialPolicyResolver implements CredentialPolicyResolver, InitializingBean {
	/**
	 * Default {@link CredentialPolicy} value.
	 */
	private DefaultCredentialPolicy defaultCredentialPolicy;
	
	@Value("${innovez.security.credential.policy.alwaysGenerateOnRegistration}")
	private boolean defaultAlwaysGenerateOnRegistration;
	
	@Value("${innovez.security.credential.policy.expirationWarningDays}")
	private Integer defaultExpirationWarningDays;
	
	@Value("${innovez.security.credential.policy.expirationWarningFrequencies}")
	private Integer defaultExpirationWarningFrequencies;
	
	@Value("${innovez.security.credential.policy.minimumLength}")
	private Integer defaultMinimumLength;
	
	@Value("${innovez.security.credential.policy.resetImmediatelyOnResetRequest}")
	private boolean defaultResetImmediatelyOnResetRequest;
	
	@Value("${innovez.security.credential.policy.expireImmediatelyOnResetRequest}")
	private boolean defaultExpireImmediatelyOnResetRequest;
	
	@Value("${innovez.security.credential.policy.resetTicketMaximumAge}")
	private Integer defaultResetTicketMaximumAge;
	
	@Value("${innovez.security.credential.policy.maximumAge}")
	private Integer defaultMaximumAge;
	
	/**
	 * FIXME
	 * 
	 * Currently this just returning default credentials policy resolved from
	 * property source. Next implementation we should improve resolving process to
	 * be aware of execution context, for example current user organization policy.
	 */
	@Override
	public CredentialPolicy resolveCredentialPolicy() {
		return defaultCredentialPolicy;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, Object> policyMap = new HashMap<String, Object>();
		policyMap.put(DefaultCredentialPolicy.ALWAYS_GENERATE_ON_REGISTRATION_KEY, defaultAlwaysGenerateOnRegistration);
		policyMap.put(DefaultCredentialPolicy.EXPIRATION_WARNING_DAYS_KEY, defaultExpirationWarningDays);
		policyMap.put(DefaultCredentialPolicy.EXPIRATION_WARNING_FREQUENCIES_KEY, defaultExpirationWarningFrequencies);
		policyMap.put(DefaultCredentialPolicy.MINIMUM_LENGTH_KEY, defaultMinimumLength);
		policyMap.put(DefaultCredentialPolicy.RESET_IMMEDIATELY_ON_RESET_REQUEST_KEY, defaultResetImmediatelyOnResetRequest);
		policyMap.put(DefaultCredentialPolicy.EXPIRE_IMMEDIATELY_ON_RESET_REQUEST_KEY, defaultExpireImmediatelyOnResetRequest);
		policyMap.put(DefaultCredentialPolicy.RESET_TICKET_MAXIMUM_AGE_KEY, defaultResetTicketMaximumAge);
		policyMap.put(DefaultCredentialPolicy.MAXIMUM_AGE_KEY, defaultMaximumAge);	
		defaultCredentialPolicy = new DefaultCredentialPolicy(policyMap);
	}
}
