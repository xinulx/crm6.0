package com.mine.App.controller.system;

import com.mine.App.common.Base.BaseController;
import com.mine.App.common.pagenation.Pagination;
import com.mine.App.common.pagenation.PaginationList;
import com.mine.App.common.util.DataHelper;
import com.mine.App.model.UserInfo;
import com.mine.App.service.system.RoleService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.ajaxanywhere.AAUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RoleAction extends BaseController {
	@Autowired
	private RoleService roleService;

	private Logger logger=Logger.getLogger(this.getClass().getName());
	private String viewName;
	//依赖注入一个名为viewName的参数,例如一个JSP文件，作为展示model的视图
	public String getViewName (){
		return this.viewName;
	}
	public void setViewName (String viewName){
		this. viewName =viewName;
	}

	public void queryRoleListPagenation(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		Map params = new HashMap();
		if(req.getParameter("roleName") != null) {
			params.put("roleName",new String(req.getParameter("roleName").getBytes("iso-8859-1"), "utf-8"));
		}
		Integer count =this.roleService.queryRoleListCount(params);
		Pagination pagination = initPagination(req, 10,count);
		List<Map> listOffers = this.roleService.queryRoleListPagenation(pagination,params);
		req.setAttribute("listOffers",listOffers);
		req.setAttribute("FW__PAGER_RECORD_TOTAL",pagination.getRecordTotal());
		if (AAUtils.isAjaxRequest(req)) {
			AAUtils.addZonesToRefresh(req, "ROLEINFO_LIST_ZONE");
		}
		req.getRequestDispatcher("pages/index/system/tablePages/roleInfoList.jsp").forward(req, res);
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

	public void addRole(HttpServletRequest request,HttpServletResponse response){
        try {
            DataHelper dh = new DataHelper(request,response);
            Map params = dh.getMapByEntry(request);
            String result = this.roleService.addRole(params);
			dh.responseData(result);
        } catch (IOException e) {
            logger.debug(e.getCause());
            e.printStackTrace();
        }
    }

    public void chkRoleCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
	    DataHelper dh = new DataHelper(request,response);
	    Map params = dh.getMapByEntry(request);
	    Map result = this.roleService.chkRoleCode(params);
	    response.getWriter().print(JSONObject.fromObject(result));
	    response.getWriter().flush();
	    response.getWriter().close();
    }

    public void qryRoleInfoById(HttpServletRequest request,HttpServletResponse response){
        try {
            DataHelper dh = new DataHelper(request,response);
            Map params = dh.getMapByEntry(request);
            Map roleInfo = this.roleService.qryRoleInfoById(params);
            dh.responseData(roleInfo);
        } catch (IOException e) {
            logger.debug(e.getCause());
            e.printStackTrace();
        }
    }

    public void editRole(HttpServletRequest request,HttpServletResponse response){
        try {
            DataHelper dh = new DataHelper(request,response);
            Map params = dh.getMapByEntry(request);
            this.roleService.editRole(params);
        } catch (IOException e) {
            logger.debug(e.getCause());
            e.printStackTrace();
        }
    }

    public void delRoleById(HttpServletRequest request,HttpServletResponse response){
		DataHelper dh = null;
    	try {
			dh = new DataHelper(request,response);
			Map params = dh.getMapByEntry(request);
			Map result = this.roleService.delRoleById(params);
			dh.responseData(result);
		} catch (IOException e) {
			logger.debug(e.getCause());
			e.printStackTrace();
		}
	}

	public void qryUserOfNoRole(HttpServletRequest request,HttpServletResponse response) throws IOException {
		DataHelper dh = new DataHelper(request,response);
		Map params = dh.getMapByEntry(request);
		List<Map<String,Object>> result = new ArrayList<>();
		result = this.roleService.qryUserOfNoRole(params);
		dh.responseData(result);
	}

	public void qryRoleInfoByOrgId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		DataHelper dh = new DataHelper(request,response);
		Map params = dh.getMapByEntry(request);
		Map<String,Object> result = new HashMap<String,Object>();
		result = this.roleService.qryRoleInfoByOrgId(params);
		response.getWriter().print(JSONObject.fromObject(result));
		response.flushBuffer();
		response.getWriter().close();
	}

	public void saveUserRoleInfo(HttpServletRequest request,HttpServletResponse response) {
        DataHelper dh = null;
        try {
            dh = new DataHelper(request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map params = null;
        try {
            params = dh.getMapByEntry(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 只能添加自己所属机构的角色权限
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		params.put("orgId",user.getOrgId());
        this.roleService.saveUserRoleInfo(params);
    }

    public void initRoleInfo(HttpServletRequest request,HttpServletResponse response){
    	String userId = request.getParameter("userId");
    	List<Map<String,Object>> roleInfo = this.roleService.qryRoleInfoByUserId(userId);
		try {
			response.getWriter().print(JSONArray.fromObject(roleInfo));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteGroupRoleById(HttpServletRequest request,HttpServletResponse response){
		try {
			DataHelper dh = new DataHelper(request,response);
			Map<Object, Object> params = dh.getMapByEntry(request);
			int n  = this.roleService.deleteGroupRoleById(params);
			dh.responseData(n);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void updateRoleOrgId(HttpServletRequest req,HttpServletResponse res){
		DataHelper dh;
		try {
			dh = new DataHelper(req, res);
			Map map = dh.getMapByEntry(req);
			int result = this.roleService.updateRoleOrgId(map);
			dh.responseData(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void queryAllRole(HttpServletRequest req,HttpServletResponse res){
		DataHelper dh;
		try {
			dh = new DataHelper(req, res);
			Map map = dh.getMapByEntry(req);
			List<Map<String,Object>> result = this.roleService.queryAllRole(map);
			for(Map<String,Object> role:result){
				role.put("iconCls","icon-role");
				map.put("roleId",role.get("roleid"));
				List<Map<String,Object>> users = this.roleService.queryAllRoleUser(map);
				if(users != null && users.size() > 0){
					role.put("leaf",false);
					for(Map<String,Object> user:users){
						user.put("iconCls","icon-user");
						user.put("leaf",true);
					}
					role.put("children",users);
				}else{
					role.put("leaf",true);
				}
			}
			dh.responseData(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}