package com.innovez.notif.samples.core.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.innovez.notif.samples.core.entity.User;
import com.innovez.notif.samples.core.entity.UserResetCredentialTicket;
import com.innovez.notif.samples.core.repository.UserResetCredentialTicketRepository;

@Service
@Transactional(readOnly=true)
public class JpaRepositoryBackedUserCredentialService implements UserCredentialService {
	private static final Logger LOGGER = LoggerFactory.getLogger(JpaRepositoryBackedUserCredentialService.class);
	
	@Autowired
	private UserCrudService userService;
	
	@Autowired
	private UserResetCredentialTicketRepository resetTicketRepository;
	
	@Autowired
	private CredentialPolicyResolver credentialPolicyResolver;
	
	@Autowired
	private PasswordGenerator passwordGenerator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ResetCredentialTicket createResetTicket(String username) {
		Assert.hasText(username, "Username parameter should not be null or empty parameter");
		Assert.isTrue(userService.isRegisteredUser(username), "Given username is not valid registered username");
		
		LOGGER.debug("Initiating reset password of user with username {}. First check whether any available ticket created before, deactivate if exits.", username);
		if(hasValidResetTicket(username)) {
			ResetCredentialTicket activeTicket = getValidResetTicket(username);
			
		}
		
		User user = userService.getUserDetails(username);
		
		CredentialPolicy credentialPolicy = credentialPolicyResolver.resolveCredentialPolicy();
		if(credentialPolicy.isResetImmediatelyOnResetRequest()) {
			LOGGER.debug("Reset credential immediately.");
			String generatedPassword = passwordGenerator.generatePassword();
			String encodedPassword = passwordEncoder.encode(generatedPassword);
			user.setPassword(encodedPassword);
			user = userService.updateUser(user);
		}
		if(credentialPolicy.isExpireImmediatelyOnResetRequest()) {
			LOGGER.debug("Expire credential immediately.");
			user.setCredentialsExpired(true);
		}
		
		UserResetCredentialTicket resetTicket = new UserResetCredentialTicket();
		resetTicket.setNumber(new Long(new Date().getTime()).toString());
		resetTicket.setUser(user);
		resetTicket.setAvailable(true);
		
		Date issuedDate = new Date();
		resetTicket.setIssuedDate(issuedDate);
		
		Integer credentialAge = credentialPolicy.getMaximumAge();
		if(credentialAge > 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, credentialAge);
			
			Date expiredDate = calendar.getTime();
			resetTicket.setExpiredDate(expiredDate);
		}
		return resetTicketRepository.save(resetTicket);
	}

	@Override
	public boolean hasValidResetTicket(String username) {
		return false;
	}

	@Override
	public ResetCredentialTicket getValidResetTicket(String username) {
		return null;
	}
	
	@Override
	public List<ResetCredentialTicket> getValidResetTicketList() {
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
