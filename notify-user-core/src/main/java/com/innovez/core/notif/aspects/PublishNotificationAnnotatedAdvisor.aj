package com.innovez.core.notif.aspects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.innovez.core.notif.Notification;
import com.innovez.core.notif.NotificationService;
import com.innovez.core.notif.annotation.Definition;
import com.innovez.core.notif.annotation.Factory;
import com.innovez.core.notif.annotation.Named;
import com.innovez.core.notif.annotation.Parameter;
import com.innovez.core.notif.annotation.PublishNotification;
import com.innovez.core.notif.support.NotificationFactory;

/**
 * Aspect type which will advising all method annotated with {@link PublishNotification}.
 * 
 * @author zakyalvan
 */
public aspect PublishNotificationAnnotatedAdvisor implements ApplicationContextAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(PublishNotificationAnnotatedAdvisor.class);
	
	/**
	 * SpEL expression parser, used for parsing parameters value, based on declaration.
	 */
	private ExpressionParser expressionParser = new SpelExpressionParser();
	
	/**
	 * Application context, we'll resolve factory object via this context
	 */
	private ApplicationContext applicationContext;
	
	@Autowired
	private NotificationService notificationService;
	
	/**
	 * Point-cut to select all {@link PublishNotification} annotated methods.
	 */
	pointcut publishNotificationAnnotatedMethods(Object targetObject, PublishNotification publishNotification) : execution(@PublishNotification * *.*(..)) && target(targetObject) && @annotation(publishNotification);
	
	/**
	 * Aspect to be executed on each {@link PublishNotification} annotated method.
	 * Here we create evaluation context, create {@link Notification} object, base on definitions or
	 * using declared factories in {@link PublishNotification} annotation.
	 * 
	 * @param targetObject
	 * @return
	 */
	Object around(Object targetObject, PublishNotification publishNotification) : publishNotificationAnnotatedMethods(targetObject, publishNotification) {
		LOGGER.debug("Before execution of publish notification method with name '{}', prepare evaluation context.", publishNotification.name());
		
		/**
		 * Create SpEL evaluation context with advised join point target object as root object.
		 */
		StandardEvaluationContext evalContext = new StandardEvaluationContext(targetObject);
		Map<String, Object> evalContextVars = new HashMap<String, Object>();
		
		/**
		 * Add all method arguments as SpEL evaluation context variables with name 'args'.
		 */
		Object[] methodArgs = thisJoinPoint.getArgs();
		evalContextVars.put("args", methodArgs);
		
		MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Annotation[][] methodArgsAnnotations = method.getParameterAnnotations();
		
		/**
		 * Looking for method arguments annotated with @Named and add them as variable in SpEL evaluation context.
		 */
		for(int i = 0; i < methodArgs.length; i++) {
			Annotation[] argAnnotations = methodArgsAnnotations[i];
			for(Annotation methodArgAnnotation : argAnnotations) {
				if(methodArgAnnotation instanceof Named) {
					String overrideArgName = ((Named) methodArgAnnotation).value();
					LOGGER.debug("Found @Named argument with name {} and value {}. Add as SpEL evaluation context variable", 
							overrideArgName, 
							methodArgs[i]);
					evalContextVars.put(overrideArgName, methodArgs[i]);
				}
			}
		}
		
		/**
		 * Proceed the method execution on advised join point.
		 */
		Object returnedObject = proceed(targetObject, publishNotification);
		
		/**
		 * Add returned object as SpEL evaluation context variable with name 'ret'
		 */
		evalContextVars.put("ret", returnedObject);
		
		evalContext.setVariables(evalContextVars);
		
		/**
		 * Now, parse and resolve global parameters.
		 */
		LOGGER.debug("Parse and resolve global parameters, based on parameters attribute of PublishNotification annotation");
		Map<String, Object> globalParams = new HashMap<String, Object>();
		for(Parameter parameter : publishNotification.parameters()) {
			String parameterExpressionString = parameter.expression();
			Expression parameterExpression = expressionParser.parseExpression(parameterExpressionString);
			globalParams.put(parameter.name(), parameterExpression.getValue(evalContext));
		}
		
		LOGGER.debug("After execution of advised method on target object, here we start sends the notifications");
		
		LOGGER.debug("Process all declared definitions, create notification object for each of them");
		for(Definition definition : publishNotification.definitions()) {
			String selectorExpressionString = definition.selector();
			if(selectorExpressionString.trim().isEmpty()) {
				selectorExpressionString = "false";
			}
			
			Expression selectorExpression = expressionParser.parseExpression(selectorExpressionString);
			if(selectorExpression.getValue(evalContext, Boolean.class)) {
				LOGGER.debug("Definition selector string '{}' evaluated to true, process definition further", selectorExpressionString);
				
				
			}
			else {
				LOGGER.debug("Definition selector string '{}' evaluated to false, abort processing", selectorExpressionString);
			}
		}
		
		LOGGER.debug("Process all declared factories, create notification object by dispatching all of them (Wit)");
		for(Factory factory : publishNotification.factories()) {
			String selectorExpressionString = factory.selector();
			if(selectorExpressionString.trim().isEmpty()) {
				selectorExpressionString = "false";
			}
			
			Expression selectorExpression = expressionParser.parseExpression(selectorExpressionString);
			if(!selectorExpression.getValue(evalContext, Boolean.class)) {
				LOGGER.debug("Factory selector string '{}' evaluated to false, processing aborted", selectorExpressionString);
				continue;
			}

			LOGGER.debug("Factory selector string '{}' evaluated to true, process further with notification factory", selectorExpressionString);
			
			/**
			 * Parse and resolve factory parameters. Factory parameters are
			 * inheriting global parameters.
			 */
			Map<String, Object> factoryParams = new HashMap<String, Object>(globalParams);
			for (Parameter factoryParam : factory.parameters()) {
				String factoryParamExpressionString = factoryParam.expression();
				if(!factoryParamExpressionString.trim().isEmpty()) {
					Expression factoryParamExpression = expressionParser.parseExpression(factoryParamExpressionString);
					factoryParams.put(factoryParam.name(), factoryParamExpression.getValue(evalContext));
				}
			}

			/**
			 * Create factory object for notification.
			 */
			Class<? extends NotificationFactory> factoryType = factory.type();
			String beanName = factory.bean();

			NotificationFactory factoryObject = null;
			if (!beanName.trim().isEmpty()) {
				try {
					factoryObject = applicationContext.getBean(beanName, factoryType);
				}
				catch (Exception e) {
					LOGGER.error("Cant resoleve bean with type {} and name {} from application context", factoryType.getName(), beanName);
					e.printStackTrace();
				}
			}
			else {
				try {
					factoryObject = factoryType.newInstance();
				} 
				catch (Exception e) {
					LOGGER.error("Cant create instance of {}, a notification factory type, based on factory definition", factoryType.getName());
					e.printStackTrace();
				}
			}

			if (factoryObject != null && factoryObject.canCreateNotification(factoryParams)) {
				LOGGER.debug("Create notification object, using notification factory type {} and parameters {}", factoryType.getName(), factoryParams.toString());
				Notification notification = factoryObject.createNotification(factoryParams);
				
				LOGGER.debug("Send the notification now!");
				notificationService.sendNotification(notification);
			}
			else {
				LOGGER.error("No factory object created or notification can't be created based on PublishNotification definitions.");
			}
		}
		
		return returnedObject;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		LOGGER.debug("Application context object injected");
		this.applicationContext = applicationContext;
	}
}
