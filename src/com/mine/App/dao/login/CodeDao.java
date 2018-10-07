package com.mine.App.dao.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mine.App.model.ValidateCode;

public class CodeDao {
	public void getCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		// 设置响应的类型格式为图片格式  
        response.setContentType("image/jpeg");  
        //禁止图像缓存。  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
          
        HttpSession session = request.getSession();  
        
        ValidateCode vCode = new ValidateCode(180,40,6,100);  
        session.setAttribute("code", vCode.getCode());
        //重置response
        response.reset();
        vCode.write(response.getOutputStream()); 
	}
	
	public void getSimpleCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		// 设置响应的类型格式为图片格式  
        response.setContentType("image/jpeg");  
        //禁止图像缓存。  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        //重置response
        response.reset();
        new ValidateCode().simpleCode(request, response);
	}
	
	public void getOtherCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
        //重置response
        response.reset();
        new ValidateCode().otherCode(request, response);
	}
}
