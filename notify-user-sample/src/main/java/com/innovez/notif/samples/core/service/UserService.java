package com.innovez.notif.samples.core.service;

import com.innovez.notif.samples.core.entity.User;

public interface UserService {
	User registerUser(String username, String password, String emailAddress);
	void startResetPasswordProcess(String username);
}
