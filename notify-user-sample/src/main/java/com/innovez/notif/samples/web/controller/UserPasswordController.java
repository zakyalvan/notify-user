package com.innovez.notif.samples.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.innovez.notif.samples.core.service.ResetCredentialTicket;
import com.innovez.notif.samples.core.service.UserCredentialService;

@Controller
@RequestMapping(value="/users")
public class UserPasswordController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserPasswordController.class);
	
	@Autowired
	private UserCredentialService credentialService;
	
	/**
	 * 
	 * @param username
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{username}/password", method=RequestMethod.PUT)
	public String createResetTicket(@PathVariable String username, Model model) {
		LOGGER.debug("Handle reset password for user {}", username);
		ResetCredentialTicket resetTicket = credentialService.createResetTicket(username);
		return "";
	}
}
