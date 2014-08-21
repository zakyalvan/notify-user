package com.innovez.notif.samples.core.entity;

import com.innovez.notif.samples.core.service.CredentialPolicy;

public class OrganizationCredentialPolicy implements CredentialPolicy {
	private Organization organization;
	private boolean resetCredentialImmediately;
	private Integer resetCredentialTicketAge;
	private Integer credentialAge;
	
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	@Override
	public Integer getExpirationWarningDays() {
		return null;
	}
	
	@Override
	public Integer getExpirationWarningFrequencies() {
		return null;
	}
	@Override
	public boolean isAlwaysGenerateOnRegistration() {
		return false;
	}

	@Override
	public Integer getMinimumLength() {
		return null;
	}

	@Override
	public boolean isResetImmediatelyOnResetRequest() {
		return resetCredentialImmediately;
	}
	public void setResetCredentialImmediately(boolean resetCredentialImmediately) {
		this.resetCredentialImmediately = resetCredentialImmediately;
	}

	@Override
	public boolean isExpireImmediatelyOnResetRequest() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Integer getResetTicketMaximumAge() {
		return resetCredentialTicketAge;
	}
	public void setResetCredentialTicketAge(Integer resetCredentialTicketAge) {
		this.resetCredentialTicketAge = resetCredentialTicketAge;
	}

	@Override
	public Integer getMaximumAge() {
		return credentialAge;
	}
	public void setCredentialAge(Integer credentialAge) {
		this.credentialAge = credentialAge;
	}
}
