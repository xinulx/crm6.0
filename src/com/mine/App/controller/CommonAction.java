package com.mine.App.controller;

import com.mine.App.common.Base.BaseController;
import com.mine.App.common.exception.BusiException;
import com.mine.App.common.pagenation.Pagination;
import com.mine.App.common.pagenation.PaginationList;
import com.mine.App.common.util.DataHelper;
import com.mine.App.service.CommonService;
import com.oreilly.servlet.MultipartRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.ajaxanywhere.AAUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class CommonAction extends BaseController {
	@Autowired
	private CommonService commonService;

	private Logger logger=Logger.getLogger(this.getClass().getName());
	private String viewName;
	//依赖注入一个名为viewName的参数,例如一个JSP文件，作为展示model的视图
	public String getViewName (){
		return this.viewName;
	}
	public void setViewName (String viewName){
		this. viewName =viewName;
	}

	/**
	 * 查询系统tab页菜单
	 * @param req,res
	 * @return ModelAndView
	 */
	public void loadSystemAllMenu(HttpServletRequest req,
								  HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");
		logger.info("--> 正在查询所有菜单...");
		Map model = new HashMap();
		String param=req.getParameter("param");
		String type=req.getParameter("type");
		String userRole=req.getParameter("userRole");
		List rstMap =new ArrayList();
		String orgIdOrName=req.getParameter("orgIdOrName");
		if(orgIdOrName!=null){
			if(orgIdOrName.trim().equals("")){
				orgIdOrName=null;
				orgIdOrName=new String(req.getParameter("orgIdOrName").getBytes("iso-8859-1"), "utf-8");
			}else{
			}
		}
		if(param.equals("00010120")&&type.equalsIgnoreCase("systemMenu")){
			rstMap=this.commonService.getMenuInfoForList(param, type);
		}else if(param.equals("00010121")&&type.equalsIgnoreCase("systemMenuItem")){
			rstMap=this.commonService.getOrgInfoForList(param, type,orgIdOrName,userRole);
		}else if(param.equals("00010121")&&type.equalsIgnoreCase("systemMenuList")){
			rstMap=this.commonService.getOrgInfoForTreeList(param, type,orgIdOrName);
		}

		res.getWriter().print(JSONArray.fromObject(rstMap));
		res.getWriter().close();
	}

	/**
	 * 查询当前点击的菜单地址
	 * @param req
	 * @param res
	 * @throws IOException
	 * @throws ServletException
	 */
	public void getMenuUrl(HttpServletRequest req,
						   HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		logger.info("获取当前菜单路径");
		String menuId=req.getParameter("menuId");
		String type=req.getParameter("type");
		Map<String,Object> url = new HashMap<>();
		if(type!=null&&type.equals("a")){
			url=this.commonService.getCurrMenuUrl(menuId);
		}
		DataHelper dh=new DataHelper(req,res);
		dh.responseData(url);
		res.getWriter().close();
	}

	/**
	 * 跳转到当前点击的菜单地址
	 * @param req
	 * @param res
	 * @throws IOException
	 * @throws ServletException
	 */
	public String toClickMenuPage(HttpServletRequest req,
								  HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		logger.info("跳转到当前菜单路径");
		String menuUrl=req.getParameter("menuUrl");
		String menuMethod=req.getParameter("menuMethod");
		String type=req.getParameter("type");
		if(type.equals("b")){
			return menuUrl + "&viewUrl=" + menuMethod;
		}else{
			return "pages/error/404";
		}
	}

	/**
	 * 跳转到系统配置登陆页面
	 * @param req
	 * @param res
	 * @throws IOException
	 * @throws ServletException
	 */
	public String systemLogin(HttpServletRequest req,
							  HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		logger.info("跳转到系统配置登陆页面。。。");
		String loginFlag=req.getParameter("loginFlag");

		String loginUrlFlag=this.commonService.getCurrLoginUrl();
		if("BS".equalsIgnoreCase(loginUrlFlag)){
			return "examLogin";
		}else if("WL".equalsIgnoreCase(loginUrlFlag)){
			return "login";
		}else{
			return "pages/error/404";
		}
	}

	/**
	 * 文件导入
	 * @param req
	 * @param res
	 * @throws IOException
	 * @throws ServletException
	 */
	public void fileUpload (HttpServletRequest req,
							HttpServletResponse res) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		String uploadPath = "WEB-INF\\classes\\upload";
		String saveDirectory = req.getSession().getServletContext().getRealPath(uploadPath);
		MultipartRequest multi = new MultipartRequest(req,saveDirectory, 100 * 1024 * 1024, "UTF-8");
		//如果有上传文件, 则保存到数据内
		Enumeration files = multi.getFileNames();
		while (files.hasMoreElements()) {
			String name = (String)files.nextElement();
			File f = multi.getFile(name);
			if(f!=null){
				//读取上传后的项目文件, 导入保存到数据中
				String fileName = multi.getFilesystemName(name);
				long size = f.length();
				res.getWriter().write(fileName +","+size+","+name);//可以返回一个JSON字符串, 在客户端做更多处理
			}
		}
	}

	public void queryOrgListPagenation(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		Map params = new HashMap();
		if(req.getParameter("orgName") != null) {
			params.put("orgName",new String(req.getParameter("orgName").getBytes("iso-8859-1"), "utf-8"));
		}
		if(req.getParameter("orgId") != null) {
			params.put("orgId",new String(req.getParameter("orgId").getBytes("iso-8859-1"), "utf-8"));
		}
		Integer count =this.commonService.queryOrgListCount(params);
		Pagination pagination = initPagination(req, 10,count);
		List<Map> listOffers = this.commonService.queryOrgListPagenation(pagination,params);
		for(Map map:listOffers){
            map.put("CREATE_DATE",map.get("CREATE_DATE").toString().substring(0,19));
            map.put("UPDATE_DATE",map.get("UPDATE_DATE").toString().substring(0,19));
        }
		req.setAttribute("listOffers",listOffers);
		req.setAttribute("FW__PAGER_RECORD_TOTAL",pagination.getRecordTotal());
		if (AAUtils.isAjaxRequest(req)) {
			AAUtils.addZonesToRefresh(req, "ORGINFO_LIST_ZONE");
		}
		req.getRequestDispatcher("pages/index/system/tablePages/orgInfoList.jsp").forward(req, res);
	}

	public void queryOrgEmpListPagenation(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		String orgId = req.getParameter("orgId");
		Map map = new HashMap();
		map.put("orgId", orgId);
		Integer count =this.commonService.queryOrgEmpListCount(map);
		Pagination pagination = initPagination(req, 10,count);
		List<Map> listOffers = this.commonService.queryOrgEmpListPagenation(pagination,map);
		req.setAttribute("listOffers",listOffers);
		req.setAttribute("orgId", orgId);
		req.setAttribute("FW__PAGER_RECORD_TOTAL",pagination.getRecordTotal());
		if (AAUtils.isAjaxRequest(req)) {
			AAUtils.addZonesToRefresh(req, "EMPINFO_LIST_ZONE");
		}
		req.getRequestDispatcher("pages/index/system/orgInfo/orgUserInfo.jsp").forward(req, res);
	}
	/**
	 *  构造分页对象
	 */
	protected Pagination initPagination(HttpServletRequest request, Integer pageSize, Integer totalAcount){
		int currentPage = 1;
		if (StringUtils.isNotBlank(request.getParameter("pager.offset"))) {
			currentPage = Integer.valueOf(
					request.getParameter("pager.offset").toString()).intValue();
			currentPage = currentPage/pageSize + 1;
			if(totalAcount%pageSize==0 && (currentPage-1)*pageSize==totalAcount){ //该代码作用是删除多页后数据如果为空自动跳转前一页
				currentPage--;
				request.setAttribute("pagerOffset", (Integer.parseInt(request.getParameter("pager.offset"))-pageSize));
			}else{
				request.setAttribute("pagerOffset", request.getParameter("pager.offset"));
			}
		}
		Pagination pagination = new PaginationList(Integer.valueOf(pageSize), Integer.valueOf(currentPage));
		pagination.setRecordTotal(totalAcount);
		request.getSession().setAttribute("pageTotal", totalAcount%pageSize == 0?totalAcount/pageSize:(totalAcount/pageSize+1));
		return pagination;
	}

	public void addProblem(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		DataHelper dh = new DataHelper(request,response);
		Map params = dh.getMapByEntry(request);
		params.put("userId",request.getSession().getAttribute("userId"));
		// 新增问题或者建议
		this.commonService.addProblem(params);
	}

	public void getProblem(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		DataHelper dh = new DataHelper(request,response);
		// 反馈列表
		List result = this.commonService.queryProblems();
		dh.responseData(result);
		dh.closeResponse();
	}

	public void querySysLogInfoList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		DataHelper dh = new DataHelper(request,response);
		Map params = dh.getMapByEntry(request);
		//分页
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		params.put("rownum",pageIndex == 0?0:pageIndex*pageSize);
		params.put("pageSize",pageSize);

        Map rstMap = new HashMap();
        String reqType = request.getParameter("type");
        if("SYS_REQUEST_LOG".equals(reqType)){
            rstMap = this.commonService.querySysLogInfoList(params);
        }else if("SERV_REQUEST_LOG".equals(reqType)){
            rstMap = this.commonService.queryServLogInfoList(params);
        }else if("USER_REQUEST_LOG".equals(reqType)){
            rstMap = this.commonService.queryUserLogInfoList(params);
        }else{
            throw new BusiException("15566513231155665","查询日志类型读取错误");
        }

        JSONObject jsonObject = JSONObject.fromObject(rstMap);
        response.getWriter().print(jsonObject);
		response.getWriter().close();
	}
}