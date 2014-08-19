package com.innovez.core.notif.config;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.innovez.core.notif.DefaultNotificationManager;
import com.innovez.core.notif.NotificationManager;
import com.innovez.core.notif.send.NotificationSender;

public class NoBrokerSupportBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		NotificationManager notificationManager = beanFactory.getBean(DefaultNotificationManager.class);
		Collection<NotificationSender> notificationSenders = new HashSet<NotificationSender>();
		for(NotificationSender notificationSender : beanFactory.getBeansOfType(NotificationSender.class).values()) {
			notificationSenders.add(notificationSender);
		}
		notificationManager.setNotificationSenders(notificationSenders);
	}
}
