package com.innovez.notif.samples.web;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.Filter;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import com.innovez.notif.samples.core.CoreConfiguration;
import com.innovez.notif.samples.core.DataConfiguration;
import com.innovez.notif.samples.core.NotifConfiguration;

/**
 * Web application initializer.
 * 
 * @author zakyalvan
 */
public class WepApplicationInitializer extends AbstractDispatcherServletInitializer {
	@Override
	protected WebApplicationContext createServletApplicationContext() {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(WebMvcConfiguration.class);
		return applicationContext;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(CoreConfiguration.class, DataConfiguration.class, NotifConfiguration.class);
		return applicationContext;
	}

	@Override
	protected Filter[] getServletFilters() {
		Collection<Filter> filters = new ArrayList<Filter>();
		OpenEntityManagerInViewFilter persistenceFilter = new OpenEntityManagerInViewFilter();
		filters.add(persistenceFilter);
		
		DelegatingFilterProxy securityFilter = new DelegatingFilterProxy("springSecurityFilterChain");
		filters.add(securityFilter);
		
		return filters.toArray(new Filter[filters.size()]);
	}
}
