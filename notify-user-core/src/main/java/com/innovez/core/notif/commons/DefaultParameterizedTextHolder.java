package com.innovez.core.notif.commons;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.format.Formatter;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.util.Assert;

@SuppressWarnings("serial")
public class DefaultParameterizedTextHolder implements ParameterizedTextHolder {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultParameterizedTextHolder.class);
	
	private static final String DEFAULT_PLACEHOLDER_PREFIX = "\\{";
	private static final String DEFAULT_PLACEHOLDER_SUFFIX = "\\}";
	
	private final String template;
	
	private String placeholderPrefix = DEFAULT_PLACEHOLDER_PREFIX;
	private String placeholderSuffix = DEFAULT_PLACEHOLDER_SUFFIX;
	
	private Pattern templatePattern;

	public DefaultParameterizedTextHolder(String template) {
		Assert.hasText(template);
		
		this.template = template;
		
		StringBuilder patternStringBuilder = new StringBuilder();
		patternStringBuilder
			.append("(")
			.append(placeholderPrefix)
			.append("([#a-zA-Z.\\(.*\\)]+)")
			.append(placeholderSuffix)
			.append(")");
		
		templatePattern = Pattern.compile(patternStringBuilder.toString());
	}
	
	@Override
	public String getRawContent() {
		return template;
	}
	
	@Override
	public String evaluateContent(Map<String, Object> parameters) {
		Assert.notNull(parameters, "Map parameters object (used as variable in evaluation context) should not be null"); 
		
		LOGGER.debug("Get content with parameters {}", parameters.toString());
		
		ExpressionParser expressionParser = new SpelExpressionParser();
		
		StandardEvaluationContext evaluationContext = new StandardEvaluationContext(this);
		evaluationContext.setVariables(parameters);
		
		String templateString = template;
		
		Matcher templateMatcher = templatePattern.matcher(templateString);
		
		Expression placeholderExpression = null;
		while(templateMatcher.find()) {
			placeholderExpression = expressionParser.parseExpression(templateMatcher.group(2));
			
			LOGGER.debug("Evaluate expression against evaluation context, all non resolvable expression will be ignored");
			try {
				Object evaluatedObject = placeholderExpression.getValue(evaluationContext);
				templateString = templateString.replace(templateMatcher.group(1), evaluatedObject.toString());
			}
			catch(SpelEvaluationException spele) {
				LOGGER.error("Error on evaluating template placeholder, just ignore it", spele);
			}
		}
		return templateString;
	}
	
	/**
	 * Used for conditionally formatting object value.
	 * 
	 * @author zakyalvan
	 *
	 * @param <T>
	 */
	public static interface TypeCheckingFormatter<T> extends Formatter<T> {
		/**
		 * Ask whether can handle given object.
		 * 
		 * @param object
		 * @return
		 */
		public boolean canHandle(Object object);
	}
	
	public static class DateTypeCheckingFormatter extends DateFormatter implements TypeCheckingFormatter<Date> {
		@Override
		public boolean canHandle(Object object) {
			return Date.class.isAssignableFrom(object.getClass());
		}		
	}
}
