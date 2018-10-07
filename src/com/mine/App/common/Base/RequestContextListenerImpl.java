package com.mine.App.common.Base;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextListener;

public class RequestContextListenerImpl extends RequestContextListener implements ApplicationContextAware{

	private ApplicationContext applicationContext;

	private ServletContext servletContext;

	private ServletRequest request;

	public void requestInitialized(ServletRequestEvent requestEvent){
		super.requestInitialized(requestEvent);
		this.servletContext = requestEvent.getServletContext();
		this.request = requestEvent.getServletRequest();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}
}