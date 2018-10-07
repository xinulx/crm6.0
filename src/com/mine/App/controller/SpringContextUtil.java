package com.mine.App.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** 
 * SpringContextUtil 
 * 
 * @author  
 */  
@Component  
public class SpringContextUtil implements ApplicationContextAware {  
  
    private static ApplicationContext applicationContext; // Spring应用上下文环境  
  
    /* 
     * 
     * 实现了ApplicationContextAware 接口，必须实现该方法； 
     * 
     * 通过传递applicationContext参数初始化成员变量applicationContext 
     */  
  
    public void setApplicationContext(ApplicationContext applicationContext)  
            throws BeansException {  
        SpringContextUtil.applicationContext = applicationContext;
    }  
  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
  
    @SuppressWarnings("unchecked")  
    public static <T> T getBean(String name) throws BeansException {  
        return (T) applicationContext.getBean(name);  
    }  
    
    public HttpServletRequest getRequest(){
    	return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }
    
    public HttpSession getSession(HttpServletRequest request){
    	return request.getSession();
    }
    
    public HttpSession getSession(){
    	return getRequest().getSession();
    }
}  