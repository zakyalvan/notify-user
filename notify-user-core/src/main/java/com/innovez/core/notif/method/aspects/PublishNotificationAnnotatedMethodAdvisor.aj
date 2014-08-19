package com.innovez.core.notif.method.aspects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.innovez.core.notif.Notification;
import com.innovez.core.notif.NotificationManager;
import com.innovez.core.notif.method.annotation.Definition;
import com.innovez.core.notif.method.annotation.Factory;
import com.innovez.core.notif.method.annotation.Named;
import com.innovez.core.notif.method.annotation.Parameter;
import com.innovez.core.notif.method.annotation.PublishNotification;
import com.innovez.core.notif.method.expression.VariableProviderRegistrar;
import com.innovez.core.notif.method.expression.VariableProviderRegistrar.VariableProviderRegistry;
import com.innovez.core.notif.method.expression.VariableProvider;
import com.innovez.core.notif.support.NotificationFactory;

/**
 * Aspect type which will advising all method annotated with {@link PublishNotification}.
 * 
 * @author zakyalvan
 */
public aspect PublishNotificationAnnotatedMethodAdvisor implements ApplicationContextAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(PublishNotificationAnnotatedMethodAdvisor.class);
	
	/**
	 * SpEL expression parser, used for parsing parameters value, based on declaration.
	 */
	private ExpressionParser expressionParser = new SpelExpressionParser();
	
	/**
	 * Application context, we'll resolve factory object via this context
	 */
	private ApplicationContext applicationContext;
	
	@Autowired
	private NotificationManager notificationService;
	
	@Autowired(required=false)
	private Set<VariableProviderRegistrar> contextVariableRegistrars;
	
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
		
		LOGGER.debug("Create SpEL evaluation context with advised join point target object as root object.");
		StandardEvaluationContext evaluationContext = new StandardEvaluationContext(targetObject);
		Map<String, Object> evalContextVars = new HashMap<String, Object>();
		
		LOGGER.debug("Add, by default, all method arguments as SpEL evaluation context variables with name 'args'.");
		Object[] methodArgs = thisJoinPoint.getArgs();
		evalContextVars.put("args", methodArgs);
		
		MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Annotation[][] methodArgsAnnotations = method.getParameterAnnotations();
		
		LOGGER.debug("Looking for @Named annoatated method arguments and add them as variable in SpEL evaluation context.");
		for(int i = 0; i < methodArgs.length; i++) {
			Annotation[] argAnnotations = methodArgsAnnotations[i];
			for(Annotation methodArgAnnotation : argAnnotations) {
				if(methodArgAnnotation instanceof Named) {
					String overrideArgName = ((Named) methodArgAnnotation).value();
					LOGGER.debug("Found @Named argument with name {} and value {}. Add as SpEL evaluation context variable", overrideArgName, methodArgs[i]);
					evalContextVars.put(overrideArgName, methodArgs[i]);
				}
			}
		}
		
		LOGGER.debug("Proceed the method {}.{} execution on advised join point", methodSignature.getDeclaringTypeName(), methodSignature.getName());
		Object returnedObject = proceed(targetObject, publishNotification);
		
		LOGGER.debug("Add returned object as SpEL evaluation context variable with name 'ret'");
		evalContextVars.put("ret", returnedObject);
		
		LOGGER.debug("Last (so we can add reserved var name), collect custom evaluation context variables, registered by developer");
		VariableProviderRegistry variableProviderRegistry = new VariableProviderRegistry(evalContextVars.keySet());
		for(VariableProviderRegistrar contextVariableRegistrar : contextVariableRegistrars) {
			contextVariableRegistrar.registerVariableProviders(variableProviderRegistry);
		}
		
		for(VariableProvider variableProvider : variableProviderRegistry.getAll()) {
			evalContextVars.put(variableProvider.getName(), variableProvider.getVariable());
		}
		
		LOGGER.debug("Set all variables into evaluation context");
		evaluationContext.setVariables(evalContextVars);
		
		LOGGER.debug("After execution of advised method on target object, here we start sends the notifications");
		Map<String, Object> globalParameters = evaluateParameters(Arrays.asList(publishNotification.parameters()), evaluationContext, new HashMap<String, Object>());
		processDefinitionDeclarations(Arrays.asList(publishNotification.definitions()), evaluationContext, globalParameters);
		processFactoryDeclarations(Arrays.asList(publishNotification.factories()), evaluationContext, globalParameters);
		
		return returnedObject;
	}
	
	/**
	 * Evaluate parameters.
	 * 
	 * @param parameterAnnotations
	 * @param evaluationContext
	 * @param globalParameters
	 * @return
	 */
	private Map<String, Object> evaluateParameters(Collection<Parameter> parameterAnnotations, EvaluationContext evaluationContext, Map<String, Object> globalParameters) {
		LOGGER.debug("Starting parameters evaluation");
		
		Map<String, Object> evaluatedParameters = new HashMap<String, Object>();
		if(globalParameters != null) {
			evaluatedParameters.putAll(globalParameters);
		}
		
		for(Parameter parameter : parameterAnnotations) {
			String parameterExpressionString = parameter.expression();
			Expression parameterExpression = expressionParser.parseExpression(parameterExpressionString);
			evaluatedParameters.put(parameter.name(), parameterExpression.getValue(evaluationContext));
		}
		return evaluatedParameters;
	}

	/**
	 * Process all {@link Definition} declarations.
	 * 
	 * @param definitionAnnotations
	 * @param evaluationContext
	 * @param globalParameters
	 * @return
	 */
	private Collection<Notification> processDefinitionDeclarations(Collection<Definition> definitionAnnotations, EvaluationContext evaluationContext, Map<String, Object> globalParameters) {
		LOGGER.debug("Process all declared definitions, create notification object for each of them");
		for(Definition definition : definitionAnnotations) {
			String selectorExpressionString = definition.guard();
			if(selectorExpressionString.trim().isEmpty()) {
				selectorExpressionString = "false";
			}
			
			Expression selectorExpression = expressionParser.parseExpression(selectorExpressionString);
			if(!selectorExpression.getValue(evaluationContext, Boolean.class)) {
				LOGGER.debug("Definition selector string '{}' evaluated to false, abort processing", selectorExpressionString);
				continue;
			}
			
			LOGGER.debug("Definition selector string '{}' evaluated to true, process definition further", selectorExpressionString);
			
			
		}
		return null;
	}
	
	/**
	 * Process all {@link Factory} declarations.
	 * 
	 * @param factoryAnnotations
	 * @param evaluationContext
	 * @param globalParameters
	 * @return
	 */
	private Collection<Notification> processFactoryDeclarations(Collection<Factory> factoryAnnotations, EvaluationContext evaluationContext, Map<String, Object> globalParameters) {
		LOGGER.debug("Process all declared factories, create notification object by dispatching all of them");
		
		for(Factory factory : factoryAnnotations) {
			String guardExpressionString = factory.guard();
			if(guardExpressionString.trim().isEmpty()) {
				guardExpressionString = "false";
			}
			
			Expression selectorExpression = expressionParser.parseExpression(guardExpressionString);
			if(!selectorExpression.getValue(evaluationContext, Boolean.class)) {
				LOGGER.debug("Factory selector string '{}' evaluated to false, processing aborted", guardExpressionString);
				continue;
			}

			LOGGER.debug("Create notification factory object.");
			Class<? extends NotificationFactory> factoryType = factory.type();
			String beanName = factory.bean();

			NotificationFactory factoryObject = null;
			if (!beanName.trim().isEmpty()) {
				try {
					factoryObject = applicationContext.getBean(beanName, factoryType);
				}
				catch (Exception e) {
					LOGGER.error("Cant resolve bean with type {} and name {} from application context", factoryType.getName(), beanName);
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

			LOGGER.debug("Parse and resolve factory parameters. Factory parameters are inheriting global parameters");
			Map<String, Object> factoryParameters = evaluateParameters(Arrays.asList(factory.parameters()), evaluationContext, globalParameters);
			
			if (factoryObject != null && factoryObject.canCreateNotification(factoryParameters)) {
				LOGGER.debug("Create notification object, using notification factory type {} and parameters {}", factoryType.getName(), factoryParameters.toString());
				Notification notification = factoryObject.createNotification(factoryParameters);
				
				LOGGER.debug("Send the notification now!");
				notificationService.sendNotification(notification);
			}
			else {
				LOGGER.error("No factory object created or notification can't be created based on PublishNotification definitions.");
			}
		}
		
		return null;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
