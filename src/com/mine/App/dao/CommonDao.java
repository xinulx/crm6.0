package com.mine.App.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mine.App.common.Base.IbaseDao;
import com.mine.App.common.pagenation.Pagination;
import com.mine.App.mapper.CommonMapper;

@Repository
public class CommonDao extends IbaseDao<CommonMapper> {

	private Logger logger=Logger.getLogger(CommonDao.class);

	public List getMenuInfoList(Map map ){
		List list=this.mapper.querySystemAllMenuForList(map);
		return list;
	}

	public List getOrgInfoList(Map map ){
		List list=this.mapper.queryOrgInfoForList(map);
		return list;
	}

	public List getDeptInfoList(Map map ){
		List list=this.mapper.queryDeptInfoForList(map);
		return list;
	}

	public Map getOrgInfoByOrgId(String orgId){
		Map map=this.mapper.queryOrgInfoByOrgId(orgId);
		return map;
	}

	public List getMenuInfoTreeList(Map map){
		List list=this.mapper.getMenuInfoTreeList(map);
		return list;
	}

	public Map<String,Object> getCurrMenuUrl(String menuId){
		return this.mapper.getCurrMenuUrl(menuId);
	}

	public String getCurrLoginUrl(){
		return this.mapper.getCurrLoginUrl();
	}

	public Integer queryOrgListCount(Map params) {
		return this.mapper.queryOrgListCount(params);
	}

	public List<Map> queryOrgListPagenation(Pagination pagination,Map params) {
		params.put("startNum", (pagination.getCurrentPage()-1)*pagination.getPageSize());
		params.put("pageSize", pagination.getPageSize());
		return this.mapper.queryOrgListPagenation(params);
	}

	public Integer queryOrgEmpListCount(Map map) {
		return this.mapper.queryOrgEmpListCount(map);
	}

	public List<Map> queryOrgEmpListPagenation(Pagination pagination, Map map) {
		super.queryForPagenationList("com.mine.App.mapper.CommonMapper.queryOrgEmpListPagenation",pagination,map);
		map.put("startNum", (pagination.getCurrentPage()-1)*pagination.getPageSize());
		map.put("pageSize", pagination.getPageSize());
		return this.mapper.queryOrgEmpListPagenation(map);
	}

	public void addProblem(Map params) {
		this.mapper.addProblem(params);
	}

	public List queryProblems() {
		return this.mapper.queryProblems();
	}

	public List<Map<String, Object>> querySysLogInfoList(Map params) {
		return this.mapper.querySysLogInfoList(params);
	}

	public Integer querySysLogInfoListCnt(Map params) {
		return this.mapper.querySysLogInfoListCnt(params);
	}

	public List<Map<String, Object>> queryServLogInfoList(Map params) {
		return this.mapper.queryServLogInfoList(params);
	}

	public Integer queryServLogInfoListCnt(Map params) {
		return this.mapper.queryServLogInfoListCnt(params);
	}

	public List<Map<String,Object>> queryUserLogInfoList(Map params) {
		return this.mapper.queryUserLogInfoList(params);
	}

	public Integer queryUserLogInfoListCnt(Map params) {
		return this.mapper.queryUserLogInfoListCnt(params);
	}

	public List<Map<String, Object>> qryRoleUser(String id) {
		return this.mapper.qryRoleUser(id);
	}
}
