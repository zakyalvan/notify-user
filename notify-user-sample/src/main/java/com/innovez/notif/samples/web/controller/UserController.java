package com.innovez.notif.samples.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.innovez.notif.samples.core.entity.User;
import com.innovez.notif.samples.core.service.UserService;

@Controller
@RequestMapping(value="/users**")
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value={"", "/"}, method=RequestMethod.POST)
	public HttpEntity<User> register() {
		LOGGER.debug("Handle user registration");
		return new ResponseEntity<User>(HttpStatus.CREATED);
	}
}
