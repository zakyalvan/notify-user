package com.innovez.notif.samples.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan
@ImportResource("classpath:/META-INF/spring/security-context.xml")
public class CoreConfiguration {
	
}
