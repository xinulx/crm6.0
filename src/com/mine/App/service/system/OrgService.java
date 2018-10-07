package com.mine.App.service.system;

import java.util.List;
import java.util.Map;

import com.mine.App.common.pagenation.Pagination;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.App.common.util.Constant;
import com.mine.App.dao.system.OrgDao;
@Service
public class OrgService implements Constant{
	private Logger logger=Logger.getLogger(OrgService.class);
	
	@Autowired
	private OrgDao orgDao;

	/**
	 * 保存机构基本信息
	 * @param paramsMap
	 */
	public void saveNewOrgInfo(Map<String,Object> paramsMap){
		this.orgDao.saveNewOrgInfo(paramsMap);
	}

	/**
	 * 查询机构信息
	 * @param params
	 * @return
	 */
	public Map qryOrgInfoByOrgId(Map params){
		return this.orgDao.qryOrgInfoByOrgId(params);
	}

	/**
	 * 删除机构信息
	 * @param inMap
	 */
	public void delOrgByorgId(Map<String, Object> inMap) {
		this.orgDao.delOrgByorgId(inMap);
	}

	public void updateOrgInfoByOrgId(Map map) {
		this.orgDao.updateOrgInfoByOrgId(map);
	}

	public List<Map<String, Object>> qryOrgInfoAll(Map map) {
		return this.orgDao.qryOrgInfoAll(map);
	}

	public Integer queryOrgDeptListCount(Map map) {
		return this.orgDao.queryOrgDeptListCount(map);
	}

	public List<Map> queryOrgDeptListPagenation(Pagination pagination, Map map) {
		return this.orgDao.queryOrgDeptListPagenation(pagination,map);
	}
}
