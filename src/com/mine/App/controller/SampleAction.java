package com.mine.App.controller;

import com.mine.App.common.Base.BaseController;
import com.mine.App.common.util.DataHelper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SampleAction extends BaseController {
     private Logger logger=Logger.getLogger(this.getClass().getName());
     private String viewName;
     //依赖注入一个名为viewName的参数,例如一个JSP文件，作为展示model的视图
     public String getViewName (){
           return this.viewName;
     }
     public void setViewName (String viewName){
           this. viewName =viewName;
     }
     
      
	public ModelAndView insert(HttpServletRequest req,
                 HttpServletResponse res) throws ServletRequestBindingException, IOException {
           Map<String, String> model = new HashMap<String, String>();
           model.put("dataList", "新增数据...");
           return new ModelAndView(getViewName(),model);
     }
     
	public ModelAndView update(HttpServletRequest req,
                 HttpServletResponse res) throws ServletRequestBindingException, IOException {
           Map<String, String> model = new HashMap<String, String>();
           res.setContentType("text/html;charset=utf-8");
           res.setCharacterEncoding("utf-8");
           model.put("dataList", "修改数据...");
           return new ModelAndView("update",model);
     }
      
     public ModelAndView delete(HttpServletRequest req,
                 HttpServletResponse res) throws ServletRequestBindingException, IOException {
           Map<String, String> model = new HashMap<String, String>();
           model.put("dataList", "删除数据...");
           return new ModelAndView(getViewName(),model);
     }
     
     public void query(HttpServletRequest req,
             HttpServletResponse res) throws ServletRequestBindingException, IOException {
    	 res.setContentType("text/html;charset=utf-8");
         res.setCharacterEncoding("utf-8");
    	 Map<String, String> model = new HashMap<String, String>();
    	 model.put("list", "测试直接响应数据...");
    	 String json=req.getParameter("json");
    	 logger.debug("getjson传递过来的："+json);
//       res.getWriter().print(model);
    	 res.getWriter().print(JSONObject.fromObject(model).toString());
     }
     
     public void test(HttpServletRequest req,
             HttpServletResponse res) throws ServletRequestBindingException, IOException {
    	 Map<String, String> model = new HashMap<String, String>();
    	 model.put("list", "测试DataHelper响应数据...");
    	 String json=req.getParameter("json");
    	 logger.debug("getjson传递过来的："+json);
    	 DataHelper dh=new DataHelper(req, res);
    	 dh.responseData(model);
     }
     
     public void loginAction(){
    	 
     }
}