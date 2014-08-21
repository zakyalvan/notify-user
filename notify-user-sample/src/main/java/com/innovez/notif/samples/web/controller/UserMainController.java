package com.innovez.notif.samples.web.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.innovez.notif.samples.core.entity.Role;
import com.innovez.notif.samples.core.entity.User;
import com.innovez.notif.samples.core.repository.RoleRepository;
import com.innovez.notif.samples.core.service.PasswordGenerator;
import com.innovez.notif.samples.core.service.UserCrudService;

@Controller
@RequestMapping(value="/users")
@SessionAttributes("command")
public class UserMainController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserMainController.class);
	
	@Autowired
	private PasswordGenerator passwordGenerator;
	
	@Autowired
	private UserCrudService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@RequestMapping(value={"", "/"}, method=RequestMethod.GET)
	public String list(Model model) {
		Collection<User> users = userService.getUserList();
		model.addAttribute("users", users);
		return "users/list";
	}
	
	@RequestMapping(value="/{username}")
	public String detalis(@PathVariable String username, Model model) {
		LOGGER.debug("Check first whether user {} is registered user", username);
		if(!userService.isRegisteredUser(username)) {
			LOGGER.debug("User {} not found", username);
		}
		
		LOGGER.debug("Show details of user {}", username);
		User user = userService.getUserDetails(username);
		model.addAttribute("user", user);
		return "users/detail";
	}
	
	@RequestMapping(value={"", "/"}, params="form=register", method=RequestMethod.GET)
	public String registerForm(Model model) {
		LOGGER.debug("Displaying user registration form");
		
		List<Role> roles = roleRepository.findAll();
		model.addAttribute("roles", roles);
		
		User user = new User();
		String generatedPassword = passwordGenerator.generatePassword();
		user.setPassword(generatedPassword);
		
		model.addAttribute("command", user);
		return "users/register";
	}
	
	@RequestMapping(value={"", "/"}, method=RequestMethod.POST)
	public String register(@Valid @ModelAttribute("command") User user, BindingResult bindingResult, SessionStatus sessionStatus) {
		LOGGER.debug("Handle user registration");
		
		if(userService.isRegisteredUser(user.getUsername())) {
			bindingResult.rejectValue("username", "users.register.error.usernameAlreadyRegistered");
		}
		if(userService.isRegisteredUserEmailAddress(user.getEmailAddress())) {
			bindingResult.rejectValue("emailAddress", "users.register.error.emailAddressAlreadyRegistered");
		}
		
		if(bindingResult.hasErrors()) {
			LOGGER.debug("Binding result contains errors {}, show the form again", bindingResult.getFieldErrors().toString());
			return "users/register";
		}
		
		LOGGER.debug("Do registration");
		userService.registerUser(user);
		
		LOGGER.debug("Complete registration session");
		sessionStatus.setComplete();
		return "redirect:/users";
	}
	
	@RequestMapping(value="/{username}", params="form=update", method=RequestMethod.GET)
	public String updateForm(@PathVariable String username, Model model) {
		LOGGER.debug("Show update user form");
		
		return "users/update";
	}
	@RequestMapping(value="/{username}", method=RequestMethod.PUT)
	public String update(@PathVariable String username, @Valid @ModelAttribute("command") User user, BindingResult bindingResult) {
		LOGGER.debug("Handle update user.");
		
		if(userService.isRegisteredUserEmailAddress(user.getEmailAddress(), username)) {
			bindingResult.rejectValue("emailAddress", "users.update.error.emailAddressAlreadyUsed");
		}
		
		if(bindingResult.hasErrors()) {
			LOGGER.debug("Submitted valus contain errors {}, reject them");
			return "users/update";
		}
		return "redirect:/users";
	}
}
