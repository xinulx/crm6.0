package com.mine.App.controller.system;

import com.mine.App.common.Base.BaseController;
import com.mine.App.common.util.DataHelper;
import com.mine.App.service.system.MenuService;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuAction extends BaseController {
	@Autowired
	private MenuService menuService;

	private Logger logger = Logger.getLogger(this.getClass().getName());
	private String viewName;

	// 依赖注入一个名为viewName的参数,例如一个JSP文件，作为展示model的视图
	public String getViewName() {
		return this.viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	/**
	 * 查询系统菜单
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
		model.put("msg", "查询成功!");

		String param = req.getParameter("param");
		String type = req.getParameter("type");
		List rstMap = new ArrayList();
		String menuIdOrName = req.getParameter("menuIdOrName");
		if (menuIdOrName != null) {
			if (menuIdOrName.trim().equals("")) {
				menuIdOrName = null;
			} else {
				menuIdOrName = new String(req.getParameter("orgIdOrName").getBytes("iso-8859-1"), "UTF-8");
			}
		}
		rstMap = this.menuService.getMenuInfoList(param, type,menuIdOrName);
		res.getWriter().print(JSONArray.fromObject(rstMap));
		res.getWriter().close();
	}

	public void queryMenuInfoById(HttpServletRequest req,
			HttpServletResponse res){
		String menuId = req.getParameter("menuId");
		Map rstMap = this.menuService.queryMenuInfoById(menuId);
		try {
			DataHelper dh =new DataHelper(req, res);
			dh.responseData(rstMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addMenu(HttpServletRequest req,
			HttpServletResponse res){
		try {
			DataHelper dh =new DataHelper(req, res);
			Map params = dh.getMapByEntry(req);
			Map rstMap = this.menuService.addMenu(params);
			dh.responseData(rstMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateMenu(HttpServletRequest req,
			HttpServletResponse res){
		try {
			DataHelper dh =new DataHelper(req, res);
			Map params = dh.getMapByEntry(req);
			Map rstMap = this.menuService.updateMenu(params);
			dh.responseData(rstMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void delMenu(HttpServletRequest req,
			HttpServletResponse res){
		DataHelper dh;
		try {
			dh = new DataHelper(req, res);
			Map params = dh.getMapByEntry(req);
			Integer count = this.menuService.delMenu(params);
			if(count > 0){
				dh.responseData(1);
			}else{
				dh.responseData(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}