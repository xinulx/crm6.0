package com.mine.App.common.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 记录日志的方面组件，需要在spring中声明该组件
 * @author db2admin
 *
 */
@Component
@Aspect
public class AopLogger {
	/**获取logger*/
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 前置通知、后置通知、最终通知
	 */
	@Before("within(com.mine.App.controller..*)")
	public void logger(){

	}
	
	/**
	 * 环绕通知
	 */
	@Around("within(com.mine.App.controller..*)")
	public Object aroundLogger(ProceedingJoinPoint p) throws Throwable{
		//目标组件类名
		String className=p.getTarget().getClass().getName();
		//调用的方法名称
		String methodName=p.getSignature().getName();
		//当前系统时间
		String currentTime=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		StringBuffer sbf=new StringBuffer();
		sbf.append("【"+currentTime+"】  ").append(className+".").append(methodName);
		//记录日志
		logger.info(sbf);
		//执行目标组件
		Object obj=p.proceed();
		return obj;
	}
	
	/**
	 * 异常通知
	 */
	@AfterThrowing(pointcut="within(com.mine.App.controller..*)",throwing="e")
	public void exceptionLogger(Exception e){
		StackTraceElement[] elems=e.getStackTrace();
		logger.error("发生异常 "+elems[0].toString());
	}
}
