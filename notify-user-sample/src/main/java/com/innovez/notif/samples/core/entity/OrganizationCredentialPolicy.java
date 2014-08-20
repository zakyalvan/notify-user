package com.innovez.notif.samples.core.entity;

import com.innovez.notif.samples.core.service.CredentialPolicy;

public class OrganizationCredentialPolicy implements CredentialPolicy {
	private Organization organization;
	private boolean resetCredentialImmediately;
	private Integer resetCredentialTicketAge;
	private Integer credentialAge;
	
	@Override
	public Integer getMinUserCredentialLength() {
		return null;
	}
	
	
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Override
	public boolean isResetCredentialImmediately() {
		return resetCredentialImmediately;
	}
	public void setResetCredentialImmediately(boolean resetCredentialImmediately) {
		this.resetCredentialImmediately = resetCredentialImmediately;
	}

	@Override
	public Integer getResetCredentialTicketAge() {
		return resetCredentialTicketAge;
	}
	public void setResetCredentialTicketAge(Integer resetCredentialTicketAge) {
		this.resetCredentialTicketAge = resetCredentialTicketAge;
	}

	@Override
	public Integer getCredentialAge() {
		return credentialAge;
	}
	public void setCredentialAge(Integer credentialAge) {
		this.credentialAge = credentialAge;
	}
}
