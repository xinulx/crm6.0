package com.mine.App.common.log4j;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.LoggingMXBean;

import org.apache.log4j.Logger;

/**
 * 日志输出组件，主要用于开发调试用
 * 此组件只是简单配置
 * @author db2admin
 *
 */
public class LoggerHelper extends Logger implements LoggingMXBean{
	
	private Logger logger;

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		logger=Logger.getLogger(Logger.class);
		this.logger = logger;
	}

	protected LoggerHelper(String name) {
		super(name);
	}

	public String getLoggerLevel(String loggerName) {
		return loggerName;
	}

	public List<String> getLoggerNames() {
		String name = this.getLogger().getClass().getName();
		List<String> list = new ArrayList<String>();
		list.add(name);
		Enumeration currentLoggers = super.getLoggerRepository().getCurrentLoggers();
		while (currentLoggers.hasMoreElements()){
			Object o = currentLoggers.nextElement();
			name = o.getClass().getName();
			list.add(name);
		}
		return list;
	}

	public String getParentLoggerName(String loggerName) {
		Logger rootLogger = super.getParent().getLoggerRepository().getRootLogger();
		return rootLogger == null?"":rootLogger.getName();
	}

	public void setLoggerLevel(String loggerName, String levelName) {
		this.name = levelName;
		this.getAppender(loggerName);
	}
	
	public void logger(String msgString,Object o){
		logger.debug(msgString);
	}
	
}
