package com.innovez.core.notif.method.aspects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.innovez.core.notif.Notification;
import com.innovez.core.notif.NotificationFactory;
import com.innovez.core.notif.NotificationManager;
import com.innovez.core.notif.commons.RecipientDetails;
import com.innovez.core.notif.commons.SimpleRecipientDetails;
import com.innovez.core.notif.method.annotation.Content;
import com.innovez.core.notif.method.annotation.Definition;
import com.innovez.core.notif.method.annotation.Factory;
import com.innovez.core.notif.method.annotation.Model;
import com.innovez.core.notif.method.annotation.Named;
import com.innovez.core.notif.method.annotation.PublishNotification;
import com.innovez.core.notif.method.annotation.Recipient;
import com.innovez.core.notif.method.annotation.Subject;
import com.innovez.core.notif.method.annotation.support.DefinitionProcessingManager;
import com.innovez.core.notif.method.annotation.support.SimpleDefinitionDetails;
import com.innovez.core.notif.method.annotation.support.TemplatedContentInfo;
import com.innovez.core.notif.method.annotation.support.TemplatedSubjectInfo;
import com.innovez.core.notif.method.expression.VariableProvider;
import com.innovez.core.notif.method.expression.VariableProviderRegistrar;
import com.innovez.core.notif.method.expression.VariableProviderRegistrar.VariableProviderRegistry;

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
	private NotificationManager notificationManager;
	
	@Autowired(required=false)
	private Set<VariableProviderRegistrar> contextVariableRegistrars = new HashSet<VariableProviderRegistrar>();
	
	@Autowired
	private DefinitionProcessingManager definitionProcessingManager;
	
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
		Map<String, Object> evaluationContextVariables = new HashMap<String, Object>();
		
		LOGGER.debug("Add, by default, all method arguments as SpEL evaluation context variables with name 'args'.");
		Object[] methodArgs = thisJoinPoint.getArgs();
		evaluationContextVariables.put("args", methodArgs);
		
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
					evaluationContextVariables.put(overrideArgName, methodArgs[i]);
				}
			}
		}
		
		LOGGER.debug("Proceed the method {}.{} execution on advised join point", methodSignature.getDeclaringTypeName(), methodSignature.getName());
		Object returnedObject = proceed(targetObject, publishNotification);
		
		if(!Void.class.isAssignableFrom(returnedObject.getClass())) {
			LOGGER.debug("Returned object {} is not void type ({}), add as SpEL evaluation context variable with name 'returned'", returnedObject.getClass().getName(), returnedObject);
			evaluationContextVariables.put("returned", returnedObject);
		}
		
		LOGGER.debug("Register custom evaluation context variable, declared using VariableProviderRegistrar");
		VariableProviderRegistry variableProviderRegistry = new VariableProviderRegistry(evaluationContextVariables.keySet());
		for(VariableProviderRegistrar contextVariableRegistrar : contextVariableRegistrars) {
			contextVariableRegistrar.registerVariableProviders(variableProviderRegistry);
		}
		
		for(VariableProvider variableProvider : variableProviderRegistry.getAll()) {
			evaluationContextVariables.put(variableProvider.getName(), variableProvider.getVariable());
		}
		
		LOGGER.debug("Set all evaluation context variables ({}) as SpEL evaluation context", evaluationContextVariables);
		evaluationContext.setVariables(evaluationContextVariables);
		
		LOGGER.debug("After execution of advised method on target object, here we start sends the notifications");
		Map<String, Object> globalModels = evaluateDeclaredModels(Arrays.asList(publishNotification.models()), evaluationContext, new HashMap<String, Object>());
		processDefinitionDeclarations(Arrays.asList(publishNotification.definitions()), evaluationContext, globalModels);
		processFactoryDeclarations(Arrays.asList(publishNotification.factories()), evaluationContext, globalModels);
		
		return returnedObject;
	}
	
	/**
	 * Evaluate declared models.
	 * 
	 * @param modelAnnotations
	 * @param evaluationContext
	 * @param globalModels
	 * @return
	 */
	private Map<String, Object> evaluateDeclaredModels(Collection<Model> modelAnnotations, EvaluationContext evaluationContext, Map<String, Object> globalModels) {
		LOGGER.debug("Starting models evaluation");
		
		Map<String, Object> evaluatedModels = new HashMap<String, Object>();
		if(globalModels != null && !globalModels.isEmpty()) {
			LOGGER.debug("Add all global models to evaluated models object, so global model will be replaced if same name found");
			evaluatedModels.putAll(globalModels);
		}
		
		for(Model modelAnnotation : modelAnnotations) {
			String modelName = modelAnnotation.name();
			
			String modelExpressionString = modelAnnotation.expression();
			Expression modelExpression = expressionParser.parseExpression(modelExpressionString);
			Object modelObject = modelExpression.getValue(evaluationContext);
			
			LOGGER.debug("Model with name '{}' and expression '{}' evaluated to obeject {}. Add to models", modelName, modelExpressionString, modelObject);
			
			evaluatedModels.put(modelName, modelObject);
		}

		return evaluatedModels;
	}

	/**
	 * Process all {@link Definition} declarations.
	 * 
	 * @param definitionAnnotations
	 * @param evaluationContext
	 * @param globalModels
	 */
	private void processDefinitionDeclarations(Collection<Definition> definitionAnnotations, EvaluationContext evaluationContext, Map<String, Object> globalModels) {
		LOGGER.debug("Process all declared definitions, create notification object for each of them");
		for(Definition definition : definitionAnnotations) {
			String guardExpressionString = definition.guard();
			if(guardExpressionString.trim().isEmpty()) {
				guardExpressionString = "false";
			}
			
			Expression guardExpression = expressionParser.parseExpression(guardExpressionString);
			if(!guardExpression.getValue(evaluationContext, Boolean.class)) {
				LOGGER.debug("Definition guard '{}' evaluated to false, abort processing", guardExpressionString);
				continue;
			}
			
			LOGGER.debug("Process recipients on definition");
			Collection<RecipientDetails> recipients = new HashSet<RecipientDetails>();
			
			Recipient recipientAnnotation = definition.recipient();
			String nameExpressionString = recipientAnnotation.name();
			String addressExpressionString = recipientAnnotation.address();
			if(!addressExpressionString.trim().isEmpty()) {
				LOGGER.debug("Evaluate name expression ({}) and address expression ({}) on recipient", nameExpressionString, addressExpressionString);
				
				Expression nameExpression = expressionParser.parseExpression(nameExpressionString);
				Expression addressExpression = expressionParser.parseExpression(addressExpressionString);
				// Nested try, this just a trick!
				try {
					String evaluatedAddress = addressExpression.getValue(evaluationContext, String.class);
					try {
						String evaluatedName = nameExpression.getValue(evaluationContext, String.class);
						recipients.add(new SimpleRecipientDetails(evaluatedName, evaluatedAddress));
					}
					catch(EvaluationException nameEvaluationException) {
						LOGGER.error("Evaluation of name expression ({}) as recipient name failed with message '{}'", nameExpressionString, nameEvaluationException.getMessage());
						recipients.add(new SimpleRecipientDetails(nameExpressionString, evaluatedAddress));
					}
				}
				catch(EvaluationException addressEvaluationException) {
					LOGGER.error("Evaluation of address expression ({}) as recipient address failed with message '{}'", addressExpressionString, addressEvaluationException.getMessage());
					recipients.add(new SimpleRecipientDetails(nameExpressionString, addressExpressionString));
				}
			}
			
			LOGGER.debug("Send notification for each recipient resolved");
			for(RecipientDetails recipient : recipients) {
				LOGGER.debug("Add recipient {} as evaluation context variable with name 'recipient', so we can refer recipient in subject and content expression (using #recipient.address or #recipient.name)", recipient);
				evaluationContext.setVariable("recipient", recipient);

				Map<String, Object> clonedGlobalModels = new HashMap<String, Object>(globalModels);
				clonedGlobalModels.put("recipient", recipient);
				
				LOGGER.debug("Process definition subject");
				Subject subjectAnnotation = definition.subject();
				String subjectTemplate = subjectAnnotation.template();
				
				LOGGER.debug("Check whether given subject template are declared in message source, if nothing found, just use declared template");
				subjectTemplate = applicationContext.getMessage(subjectTemplate, new Object[] {}, subjectTemplate, LocaleContextHolder.getLocale());
				
				Collection<Model> subjectModelAnnotations = Arrays.asList(subjectAnnotation.models());
				Map<String, Object> subjectModels = evaluateDeclaredModels(subjectModelAnnotations, evaluationContext, clonedGlobalModels);
				TemplatedSubjectInfo subject = new TemplatedSubjectInfo(subjectTemplate, subjectModels);
				
				LOGGER.debug("Process definition content");
				Content contentAnnotation = definition.content();
				String contentTemplate = contentAnnotation.template();
				
				LOGGER.debug("Check whether given content template are declared in message source, if nothing found, just use declared template");
				contentTemplate = applicationContext.getMessage(contentTemplate, new Object[] {}, contentTemplate, LocaleContextHolder.getLocale());
				
				Collection<Model> contentModelAnnotations = Arrays.asList(contentAnnotation.models());
				Map<String, Object> contentModels = evaluateDeclaredModels(contentModelAnnotations, evaluationContext, clonedGlobalModels);
				TemplatedContentInfo content = new TemplatedContentInfo(contentTemplate, contentModels);
				
				SimpleDefinitionDetails definitionDetails = new SimpleDefinitionDetails(definition.type(), recipients, subject, content);
				
				/**
				 * WARNING
				 * 
				 * We should process and send here, before 'recipient' variable in evaluation context changed for next recipient.
				 */
				Collection<Notification> notifications = definitionProcessingManager.processDefinition(definitionDetails);
				notificationManager.sendNotifications(notifications);
			}
			// Just ensuring 'recipient' evaluation context variable removed.
			evaluationContext.setVariable("recipient", null);
		}
	}
	
	/**
	 * Process all {@link Factory} declarations.
	 * 
	 * @param factoryAnnotations
	 * @param evaluationContext
	 * @param globalModels
	 */
	private void processFactoryDeclarations(Collection<Factory> factoryAnnotations, EvaluationContext evaluationContext, Map<String, Object> globalModels) {
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

			LOGGER.debug("Parse and resolve factory models. Factory models are inheriting global models");
			Map<String, Object> factoryModels = evaluateDeclaredModels(Arrays.asList(factory.models()), evaluationContext, globalModels);
			
			if (factoryObject != null && factoryObject.canCreateNotification(factoryModels)) {
				LOGGER.debug("Create notification object, using notification factory type {} and models {}", factoryType.getName(), factoryModels.toString());
				Notification notification = factoryObject.createNotification(factoryModels);
				
				LOGGER.debug("Send the notification now!");
				notificationManager.sendNotification(notification);
			}
			else {
				LOGGER.error("No factory object created or notification can't be created based on PublishNotification definitions.");
			}
		}
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
