package com.innovez.core.notif.config;

import java.util.Map;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jms.connection.CachingConnectionFactory;

import com.innovez.core.notif.DefaultNotificationManager;
import com.innovez.core.notif.method.aspects.PublishNotificationAnnotatedMethodAdvisor;

/**
 * {@link ImportBeanDefinitionRegistrar} for definition of required beans in
 * notification support submodule. Import this type in your configuration type,
 * of define this type as bean on xml context configuration.
 * 
 * @author zakyalvan
 */
public class NotificationBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
	@Value("#{innovez.notification.jms.broker.url}")
	private String brokerUrl;
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableNotification.class.getName());
		boolean enableActiveMQ = (Boolean) annotationAttributes.get("enableActiveMQ");
//		
//		brokerService.setBrokerName("NotificationBroker");
//		brokerService.setPersistent(true);
//		brokerService.setUseShutdownHook(true);
//		brokerService.addConnector("tcp://localhost:61616");
		
		BeanDefinition brokerServiceDefinition = BeanDefinitionBuilder.rootBeanDefinition(BrokerService.class)
				.addPropertyValue("brokerName", "NotificationBroker")
				.addPropertyValue("setPersistence", true)
				.addPropertyValue("useShutdownHook", true)
				.addPropertyValue("networkConnectorURIs", new String[] {"tcp://localhost:61616"})
				.getBeanDefinition();
		registry.registerBeanDefinition("brokerService", brokerServiceDefinition);
		
		BeanDefinition cachingConnectionFactoryDefinition = BeanDefinitionBuilder.rootBeanDefinition(CachingConnectionFactory.class)
				.addConstructorArgValue(new ActiveMQConnectionFactory(brokerUrl))
				.getBeanDefinition();
		registry.registerBeanDefinition("connectionFactory", cachingConnectionFactoryDefinition);
		
		BeanDefinition jmsBrokerSupportBeanFactoryPostProcessorDefinition = BeanDefinitionBuilder.rootBeanDefinition(JmsBrokerSupportBeanFactoryPostProcessor.class)
					.getBeanDefinition();
		registry.registerBeanDefinition("jmsBrokerSupportBeanFactoryPostProcessor", jmsBrokerSupportBeanFactoryPostProcessorDefinition);
		
		BeanDefinition publishNotificationAdvisorDefinition = BeanDefinitionBuilder.rootBeanDefinition(PublishNotificationAnnotatedMethodAdvisor.class, "aspectOf")
				.getBeanDefinition();
		registry.registerBeanDefinition("publishNotificationAdvisor", publishNotificationAdvisorDefinition);
		
		BeanDefinition notificationManagerDefinition = BeanDefinitionBuilder.rootBeanDefinition(DefaultNotificationManager.class)
				.getBeanDefinition();
		registry.registerBeanDefinition("notificationManager", notificationManagerDefinition);
	}
}
