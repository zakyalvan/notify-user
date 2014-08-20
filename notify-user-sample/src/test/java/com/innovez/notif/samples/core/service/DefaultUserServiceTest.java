package com.innovez.notif.samples.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.innovez.notif.samples.core.CoreConfiguration;
import com.innovez.notif.samples.core.DataConfiguration;
import com.innovez.notif.samples.core.NotifConfiguration;

@ContextConfiguration(classes={
	CoreConfiguration.class, 
	DataConfiguration.class, 
	NotifConfiguration.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultUserServiceTest {
	@Autowired
	private UserCrudService userService;
	
	@Test
	public void testSendNotification() {
		
	}
}
