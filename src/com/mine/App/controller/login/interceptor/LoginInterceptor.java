package com.mine.App.controller.login.interceptor;

import com.mine.App.model.UserInfo;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 登陆拦截器
 * @author db2admin
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
	private Logger log=Logger.getLogger(this.getClass().getName());
	 
    /**  
     * 在业务处理器处理请求之前被调用  
     * 如果返回false  
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
     * 如果返回true  
     *    执行下一个拦截器,直到所有的拦截器都执行完毕  
     *    再执行被拦截的Controller  
     *    然后进入拦截器链,  
     *    从最后一个拦截器往回执行所有的postHandle()  
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()  
     */    
    public boolean preHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler) throws Exception {    
        if ("GET".equalsIgnoreCase(request.getMethod())) {  
              
        }  
        String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());  
        String loginUrl="/crm/loginAction.shtml";
        String codeUrl="/crm/systemAction.shtml";
        String methodName=request.getParameter("method");
        if(requestUri.equals(loginUrl)&&methodName.equals("toLoginAction")){
        	return true;
        }
        if(requestUri.equals(loginUrl)&&methodName.equals("chkAndQryRole")){
            return true;
        }
        if(requestUri.equals(loginUrl)&&methodName.equals("showCode")){
        	return true;
        }
        if(requestUri.equals(loginUrl)&&methodName.equals("modiPasswordAction")){
        	return true;
        }
        if(requestUri.equals(codeUrl)&&methodName.equals("systemLogin")){
        	return true;
        }
        log.info("\n※※※※※※※※※※※※※※※※※※※※※※※※※※※本次请求信息※※※※※※※※※※※※※※※※※※※※※※※※※※※" +
                 "\n                                       REQUEST_URI         :"+requestUri+
                 "\n                                       CONTEXT_PATH        :"+contextPath+
                 "\n                                       URL                 :"+url+
                 "\n                                       METHOD_NAME         :"+methodName+
                 "\n                                       ACTION_PATH         :"+request.getServletPath() +
                 "\n※※※※※※※※※※※※※※※※※※※※※※※※※※※本次请求信息※※※※※※※※※※※※※※※※※※※※※※※※※※※");
        UserInfo user =  (UserInfo)request.getSession().getAttribute("user");
        if(user == null){  
            log.info("Interceptor：跳转到login页面！"); 
            toLogin(request, response, url);
            return false;  
        }else  
            return true;     
    }

	private void toLogin(HttpServletRequest request,
			HttpServletResponse response, String url) throws ServletException,
			IOException {
        String[] count=url.split("/");
        switch (count.length) {
            case 2:
            request.getRequestDispatcher("login.html").forward(request, response);
            break;
            case 3:
            request.getRequestDispatcher("../login.html").forward(request, response);
            break;
            case 4:
            request.getRequestDispatcher("../../login.html").forward(request, response);
        }
	}
    
    /** 
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
     * 可在modelAndView中加入数据，比如当前时间 
     */  
    public void postHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler,    
            ModelAndView modelAndView) throws Exception {     
        HttpSession session=request.getSession();
        if(modelAndView != null){  //加入当前时间    
            modelAndView.addObject("msg", "您已登录!"); 
            String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            modelAndView.addObject("loginDate", date); 
            //将登陆时间存储到session
            session.setAttribute("loginDate", date);
        }    
    }    
    
    /**  
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等   
     *   
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()  
     */
    @Override    
    public void afterCompletion(HttpServletRequest request,    
            HttpServletResponse response, Object handler, Exception ex)    
            throws Exception {
    	ServletContext servletContext=request.getSession().getServletContext();  
    	WebApplicationContext webApplicationContext = (WebApplicationContext)servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        System.out.println(webApplicationContext);
    }
}
