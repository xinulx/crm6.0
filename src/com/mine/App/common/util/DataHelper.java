package com.mine.App.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 处理请求返回结果
 *
 * 使用json工具jar将不同类型的结果集以json字符串形式返回，页面可以直接输出，也可以用ajax接收
 * 注意：ajax请求数据类型必须是json，否则接收的数据是json字符串而不是json对象
 * @author wangshibao 2015-06-26 15:26:39
 *
 */
public class DataHelper {
	private Logger logger=Logger.getLogger(this.getClass().getName());
	/**
	 * 请求对象
	 */
	private HttpServletRequest request;
	/**
	 * 响应对象
	 */
	private HttpServletResponse response;


	public DataHelper() {
		logger.info("初始化DataHelper...");
	}

	public DataHelper(HttpServletRequest request) throws IOException {
		logger.info("初始化DataHelper...");
		this.request = request;
		this.request.setCharacterEncoding("UTF-8");
	}

	public DataHelper(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("初始化DataHelper...");
		this.request = request;
		this.response = response;
		this.response.setCharacterEncoding("UTF-8");
		this.request.setCharacterEncoding("UTF-8");
		this.response.setContentType("text/html;charset=utf-8");
	}

	/**
	 * 返回json对象
	 * @param obj
	 * @throws IOException
	 */
	public void getJSONObject(Object obj) throws IOException{
		response.getWriter().print(JSONObject.fromObject(obj));
		this.closeResponse();
	}

	/**
	 * 返回json数组对象
	 * @param obj
	 * @throws IOException
	 */
	public void getJSONObject(Object[] obj) throws IOException{
		response.getWriter().print(JSONObject.fromObject(obj));
		this.closeResponse();
	}

	/**
	 * 返回json数组集合
	 * @param obj
	 * @throws IOException
	 */
	public void getJSONArray(Object[] obj) throws IOException{
		response.getWriter().print(JSONArray.fromObject(obj));
		this.closeResponse();
	}

	/**
	 * 返回json对象集合
	 * @param list
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void getJSONOArray(List list) throws IOException{
		response.getWriter().print(JSONArray.fromObject(list));
		this.closeResponse();
	}

	/**
	 * 响应返回对象
	 * @param obj
	 * @throws IOException
	 */
	public void responseData(Object obj) throws IOException{
		ResponseData rd=new ResponseData();
		rd.setData(obj);
//		logger.info("返回resnponseData:"+rd.toString());
		response.getWriter().print(JSONObject.fromObject(rd));
		this.closeResponse();
	}

	/**
	 * 响应返回集合
	 * @param list
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void responseData(List list) throws IOException{
		ResponseData rd=new ResponseData();
		rd.setData(list);
//		logger.info("返回resnponseData:"+rd.toString());
		response.getWriter().print(JSONArray.fromObject(rd));
		this.closeResponse();
	}

	/**
	 * 关闭输入流
	 * @throws IOException
	 */
	public void closeResponse() throws IOException{
		if(response.getWriter()!=null){
			response.getWriter().close();
		}
	}

	/**
	 * 枚举方法获取request所有请求参数
	 */
	public Map<String,Object> getMapByEnu(HttpServletRequest request){
		Enumeration<?> enu=request.getParameterNames();
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		while(enu.hasMoreElements()){
			String paraName = (String)enu.nextElement();
			String paraValue = request.getParameter(paraName);
			rtnMap.put(paraName, paraValue);
		}
		return rtnMap;
	}

	/**
	 * 通过entry遍历获取参数
	 * @param request
	 * @return
	 */
	public Map<Object,Object> getMapByEntry(HttpServletRequest request) throws UnsupportedEncodingException {
		Map<?, ?> map=request.getParameterMap();
		Set<?> keSet=map.entrySet();
		Map<Object,Object> rtnMap = new HashMap<Object,Object>();
		for(Iterator<?> itr=keSet.iterator();itr.hasNext();){
			Map.Entry me=(Map.Entry)itr.next();
			Object ok=me.getKey();
			Object ov=me.getValue();
			String[] value=new String[1];
			if(ov instanceof String[]){
				value=(String[])ov;
			}else{
				value[0]=ov.toString();
			}

			for(int k=0;k<value.length;k++){
				String s = value[k];
//				s = new String(s.getBytes("ISO-8859-1"),"UTF-8");
				rtnMap.put(ok, s);
			}
		}
		return rtnMap;
	}

	/**
	 * 视图跳转
	 * @throws IOException
	 * @throws ServletException
	 */
	public void forward(String viewName) throws ServletException, IOException{
		this.request.getRequestDispatcher(viewName).forward(request, response);
	}
}
