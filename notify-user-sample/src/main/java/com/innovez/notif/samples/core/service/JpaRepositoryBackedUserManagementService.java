package com.innovez.notif.samples.core.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.innovez.core.notif.method.annotation.PublishNotification;
import com.innovez.notif.samples.core.entity.User;
import com.innovez.notif.samples.core.repository.UserRepository;

@Service
@Transactional(readOnly=true)
public class JpaRepositoryBackedUserManagementService implements UserCrudService {
	private static final Logger LOGGER = LoggerFactory.getLogger(JpaRepositoryBackedUserManagementService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordGenerator passwordGenerator;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	@PublishNotification(name="register-user-notif")
	public User registerUser(User user) {
		Assert.notNull(user);
		Assert.isTrue(!isRegisteredUser(user.getUsername()), "Given username already registered for other user.");
		LOGGER.debug("Register user");
		return null;
	}

	@Override
	public boolean isRegisteredUser(String username) {
		Assert.hasText(username);
		return userRepository.exists(username);
	}

	@Override
	public boolean isRegisteredUserEmailAddress(String emailAddress, String... excludeUsernames) {
		Assert.hasText(emailAddress);
		if(excludeUsernames.length > 0) {
			return false;
		}
		else {
			return false;
		}
	}

	@Override
	public User getUserDetails(String username) {
		Assert.hasText(username);
		Assert.isTrue(isRegisteredUser(username));
		return userRepository.findOne(username);
	}

	@Override
	public Collection<User> getUserList() {
		return userRepository.findAll();
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
