package com.innovez.notif.samples.core.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultCredentialPolicyResolver implements CredentialPolicyResolver, InitializingBean {
	private DefaultCredentialPolicy defaultCredentialPolicy;
	
	@Value("${innovez.security.credential.policy.minUserCredentialLength}")
	private Integer defaultMinUserCredentialLength;
	@Value("${innovez.security.credential.policy.resetCredentialImmediately}")
	private boolean defaultResetCredentialImmediately;
	@Value("${innovez.security.credential.policy.resetCredentialTicketAge}")
	private Integer defaultResetCredentialTicketAge;
	@Value("${innovez.security.credential.policy.credentialAge}")
	private Integer defaultCredentialAge;
	
	/**
	 * FIXME
	 * 
	 * Currently this just returning default credentials policy resolved from
	 * property source. Next implementation we should improve resolve process to
	 * be aware of context, for example current user organization policy
	 */
	@Override
	public CredentialPolicy resolveCredentialPolicy() {
		return defaultCredentialPolicy;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, Object> policyMap = new HashMap<String, Object>();
		policyMap.put(DefaultCredentialPolicy.MIN_USER_CREDENTIAL_LENGTH_KEY, defaultMinUserCredentialLength);
		policyMap.put(DefaultCredentialPolicy.RESET_CREDENTIAL_IMMEDIATELY_KEY, defaultResetCredentialImmediately);
		policyMap.put(DefaultCredentialPolicy.RESET_CREDENTIAL_TICKET_AGE_KEY, defaultResetCredentialTicketAge);
		policyMap.put(DefaultCredentialPolicy.CREDENTIAL_AGE_KEY, defaultCredentialAge);
		
		defaultCredentialPolicy = new DefaultCredentialPolicy(policyMap);
	}
}
