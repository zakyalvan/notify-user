package com.innovez.notif.samples.core.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.innovez.notif.samples.core.entity.User;

public interface UserCrudService {
	User registerUser(User user);
	boolean isRegisteredUser(String username);
	boolean isRegisteredUserEmailAddress(String emailAddress, String... excludeUsernames);
	User getUserDetails(String username);
	Collection<User> getUserList();
	Page<User> getPagedUserList(Pageable pageable);
	User updateUser(User user);
	void deleteUser(String username);
}
