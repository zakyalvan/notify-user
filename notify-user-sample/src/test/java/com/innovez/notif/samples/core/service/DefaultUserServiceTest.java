package com.innovez.notif.samples.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.innovez.notif.samples.core.CoreConfiguration;
import com.innovez.notif.samples.core.DataConfiguration;
import com.innovez.notif.samples.core.NotifConfiguration;
import com.innovez.notif.samples.core.entity.User;

@ContextConfiguration(classes={
	CoreConfiguration.class, 
	DataConfiguration.class, 
	NotifConfiguration.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultUserServiceTest {
	@Autowired
	private UserService userService;
	
	@Test
	public void testSendNotification() {
		User user = userService.registerUser("zakyalvan", "1234", "zakyalvan@gmail.com");
	}
}
