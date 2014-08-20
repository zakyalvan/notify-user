package com.innovez.notif.samples.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class JpaRepositoryBackedUserCredentialService implements UserCredentialService {
	@Autowired
	private CredentialPolicyResolver credentialPolicyResolver;
	
	@Autowired
	private PasswordGenerator passwordGenerator;
	
	@Override
	public ResetCredentialTicket resetUserPassword(String username) {
		return null;
	}

	@Override
	public boolean hasResetCredentialTicket(String username) {
		return false;
	}

	@Override
	public ResetCredentialTicket getResetCredentialTicket(String username) {
		return null;
	}

	@Override
	public ResetCredentialTicket activateUser(String username, String ticketNumber) {
		return null;
	}

	@Override
	public void updateUserPassword(String username, String oldPassword, String newPassword) {
		
	}
}
