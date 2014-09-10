package com.innovez.notif.samples.core.service;

import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.innovez.core.notif.method.annotation.Content;
import com.innovez.core.notif.method.annotation.Definition;
import com.innovez.core.notif.method.annotation.Model;
import com.innovez.core.notif.method.annotation.Named;
import com.innovez.core.notif.method.annotation.PublishNotification;
import com.innovez.core.notif.method.annotation.Recipient;
import com.innovez.core.notif.method.annotation.Subject;
import com.innovez.notif.samples.core.entity.User;
import com.innovez.notif.samples.core.repository.UserRepository;

@Service
@Transactional(readOnly=true)
public class JpaRepositoryBackedUserCrudService implements UserCrudService {
	private static final Logger LOGGER = LoggerFactory.getLogger(JpaRepositoryBackedUserCrudService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CredentialPolicyResolver credentialPolicyResolver;
	
	@Autowired
	private PasswordGenerator passwordGenerator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	@PublishNotification(name="register-user", definitions={
		@Definition(
			recipient=@Recipient(name="#user.fullName", address="#user.emailAddress"),
			subject=@Subject(
				template="Registration Details of {#fullName}", 
				models=@Model(name="fullName", expression="#user.fullName")
			),
			content=@Content(
				template="Hello {#user.fullName},\n\n Your account already registered on JOS, following details of your account's credentials.\n Your username is {#user.username} and password {#user.password}",
				models=@Model(name="user", expression="#user")
			)
		)
	})
	public User registerUser(@Named("user") User user) {
		Assert.notNull(user, "User parameter should not be null.");
		Assert.isTrue(!isRegisteredUser(user.getUsername()), "Given username already registered for other user.");
		LOGGER.debug("Register user");
		
		String userPassword = user.getPassword();
		
		CredentialPolicy credentialPolicy = credentialPolicyResolver.resolveCredentialPolicy();
		if(credentialPolicy.isAlwaysGenerateOnRegistration() || userPassword.trim().isEmpty()) {
			userPassword = passwordGenerator.generatePassword();
		}
		
		String encodedUserPassword = passwordEncoder.encode(userPassword);
		user.setPassword(encodedUserPassword);
		
		return userRepository.save(user);
	}

	@Override
	public boolean isRegisteredUser(String username) {
		if(!StringUtils.hasText(username)) {
			return false;
		}
		return userRepository.exists(username);
	}

	@Override
	public boolean isRegisteredUserEmailAddress(String emailAddress, String... excludeUsernames) {
		if(!StringUtils.hasText(emailAddress)) {
			return false;
		}
		
		if(excludeUsernames.length > 0) {
			return userRepository.existsByEmailAddressIgnoreCaseExcludeUsernames(emailAddress, Arrays.asList(excludeUsernames));
		}
		else {
			return userRepository.existsByEmailAddressIgnoreCase(emailAddress);
		}
	}

	@Override
	public User getUserDetails(String username) {
		Assert.hasText(username, "Username parameter should not be null or empty text");
		Assert.isTrue(isRegisteredUser(username), "Username parameter is not valid registered user");
		return userRepository.findOne(username);
	}

	@Override
	public Collection<User> getUserList() {
		Sort sort = new Sort("username", "fullName");
		return userRepository.findAll(sort);
	}

	@Override
	public Page<User> getPagedUserList(Pageable pageable) {
		Assert.notNull(pageable);
		return userRepository.findAll(pageable);
	}

	@Override
	public User updateUser(User user) {
		Assert.notNull(user, "User object parameter should not be null");
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(String username) {
		Assert.isTrue(isRegisteredUser(username));
		User user = getUserDetails(username);
		userRepository.delete(user);
	}
}
