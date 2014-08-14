package com.innovez.core.notif.config;

import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.innovez.core.notif.aspects.PublishNotificationAnnotatedAdvisor;

/**
 * {@link ImportBeanDefinitionRegistrar} for definition of required beans in
 * notification support submodule. Import this type in your configuration type,
 * of define this type as bean on xml context configuration.
 * 
 * @author zakyalvan
 */
public class NotificationBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableNotification.class.getName());
		boolean enableActiveMQ = (Boolean) annotationAttributes.get("enableActiveMQ");
		
		BeanDefinition publishNotificationAdvisorDefinition = BeanDefinitionBuilder.rootBeanDefinition(PublishNotificationAnnotatedAdvisor.class, "aspectOf")
				.getBeanDefinition();
		registry.registerBeanDefinition("publishNotificationAdvisor", publishNotificationAdvisorDefinition);
		
		
	}
}
