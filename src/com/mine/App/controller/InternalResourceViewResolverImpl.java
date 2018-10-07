package com.mine.App.controller;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mine.App.common.util.Constant;
/**
 * 扩展视图解析器
 * @author Administrator
 *
 */
public class InternalResourceViewResolverImpl extends InternalResourceViewResolver {
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		InternalResourceView view = (InternalResourceView)super.buildView(viewName);
		String[] url = view.getUrl().split("&");
		if(url.length == 1){
			view.setUrl(url[0]+"."+Constant.HTML_PAGE);
		}else{
			view.setUrl(url[0]+"."+Constant.HTML_PAGE + "?" + url[1]);
		}
		return view;
	}
}
