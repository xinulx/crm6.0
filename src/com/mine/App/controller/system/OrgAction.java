package com.mine.App.controller.system;

import com.mine.App.common.Base.BaseController;
import com.mine.App.common.pagenation.Pagination;
import com.mine.App.common.pagenation.PaginationList;
import com.mine.App.common.util.DataHelper;
import com.mine.App.service.system.OrgService;
import org.ajaxanywhere.AAUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrgAction extends BaseController {
	
	@Autowired
	public OrgService orgService;

	/**
	 * 保存机构基本信息
	 * @param req
	 * @param res
	 */
	public void saveNewOrgInfo(HttpServletRequest req,
            HttpServletResponse res){
		DataHelper dh;
		try {
			dh = new DataHelper(req, res);
			Map map = dh.getMapByEntry(req);
			this.orgService.saveNewOrgInfo(map);
			dh.responseData(1);
	    	dh.closeResponse();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询机构信息
	 * @param req
	 * @param res
	 */
	public void showOrgInfoForView(HttpServletRequest req,
            HttpServletResponse res){
		DataHelper dh;
		try {
			dh = new DataHelper(req, res);
			Map map = dh.getMapByEntry(req);
			Map orgInfo = this.orgService.qryOrgInfoByOrgId(map);
			dh.forward("pages/index/system/view/orgInfoView.jsp");
			req.setAttribute("orgInfo",orgInfo);
			dh.responseData(1);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除机构信息
	 * @param req
	 * @param res
	 */
	public void delOrgByorgId(HttpServletRequest req,HttpServletResponse res){
		String params = req.getParameter("orgId");
		// 或取RID 和 orgId
		String orgId = params.split(",")[0];
		String rid = params.split(",")[1];
		Map<String,Object> inMap = new HashMap<String,Object>();
		inMap.put("orgId",orgId);
		inMap.put("rid",rid);
		// 直接删除机构信息
		this.orgService.delOrgByorgId(inMap);
	}

	public void updateOrgInfoByOrgId(HttpServletRequest req,HttpServletResponse res){
		DataHelper dh;
		try {
			dh = new DataHelper(req, res);
			Map map = dh.getMapByEntry(req);
			this.orgService.updateOrgInfoByOrgId(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void qryOrgInfoAll(HttpServletRequest req,HttpServletResponse res){
		DataHelper dh;
		try {
			dh = new DataHelper(req, res);
			Map map = dh.getMapByEntry(req);
			List<Map<String,Object>> result = this.orgService.qryOrgInfoAll(map);
			dh.responseData(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void queryOrgDeptListPagenation(HttpServletRequest req,HttpServletResponse res){
        String orgId = req.getParameter("orgId");
        Map map = new HashMap();
        map.put("orgId", orgId);
        Integer count =this.orgService.queryOrgDeptListCount(map);
        Pagination pagination = initPagination(req, 10,count);
        List<Map> listOffers = this.orgService.queryOrgDeptListPagenation(pagination,map);
        req.setAttribute("listOffers",listOffers);
        req.setAttribute("orgId", orgId);
        req.setAttribute("FW__PAGER_RECORD_TOTAL",pagination.getRecordTotal());
        if (AAUtils.isAjaxRequest(req)) {
            AAUtils.addZonesToRefresh(req, "DEPTINFO_LIST_ZONE");
        }
        try {
            req.getRequestDispatcher("pages/index/system/orgInfo/orgDeptInfo.jsp").forward(req, res);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}