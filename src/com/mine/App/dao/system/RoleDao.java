package com.mine.App.dao.system;

import com.mine.App.common.Base.IbaseDao;
import com.mine.App.common.pagenation.Pagination;
import com.mine.App.mapper.RoleMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDao extends IbaseDao<RoleMapper> {

	private Logger logger=Logger.getLogger(RoleDao.class);

	public Integer queryOrgListCount(Map params) {
		return this.mapper.queryRoleListCount(params);
	}

	public List<Map> queryOrgListPagenation(Pagination pagination,Map params) {
		params.put("startNum", (pagination.getCurrentPage()-1)*pagination.getPageSize());
		params.put("pageSize", pagination.getPageSize());
		return this.mapper.queryRoleListPagenation(params);
	}

	public String addRole(Map params){
		this.mapper.addRole(params);
		return "";
	}

	public Map qryRoleInfoById(Map params) {
		return this.mapper.qryRoleInfoById(params);
	}

	public void editRole(Map params) {
		this.mapper.editRole(params);
	}

	public Map delRoleById(Map params) {
		Map<String,Object> result = new HashMap<String,Object>();
		Integer num = this.mapper.qryRoleByIdOfEff(params);
		if(num > 0){
			result.put("code","1100");
			result.put("desc","角色正在生效中...,不能删除！");
		}else{
			this.mapper.delRoleById(params);
			result.put("code","1000");
			result.put("desc","删除成功！");
		}
		return result;
	}

	public List<Map<String, Object>> qryUserOfNoRole(Map params) {
		return this.mapper.qryUserOfNoRole(params);
	}

	public void saveUserRoleInfo(Map params) {
		this.mapper.saveUserRoleInfo(params);
	}

	public Map<String, Object> qryRoleInfoByOrgId(Map params) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<>();
		if(!"".equals(params.get("userId"))){
			params.put("userId",params.get("userId").toString().substring(10));
		}
		list = this.mapper.qryRoleInfoByOrgId(params);
		result.put("data",list);
		result.put("total",0);
		return result;
	}

	public Map chkRoleCode(Map params) {
		Map<String,Object> result = new HashMap<String,Object>();
		if(params.get("roleCode") != null && !params.get("roleCode").equals("")){
			Integer cnt = this.mapper.qryRoleByRoleCode(params);
			if(cnt > 0){
				result.put("getdata","角色编码已存在");
			}else{
				result.put("getdata","true");
			}
		}else{
			result.put("getdata","true");

		}
		return result;
	}

	public List<Map<String, Object>> qryRoleInfoByUserId(String userId) {
		return this.mapper.qryRoleInfoByUserId(userId);
	}

	public int deleteGroupRoleById(Map<Object, Object> params) {
		return this.mapper.deleteGroupRoleById(params);
	}

	public int updateRoleOrgId(Map map) {
		return this.mapper.updateRoleOrgId(map);
	}

	public List<Map<String, Object>> queryAllRole(Map map) {
		return this.mapper.queryAllRole(map);
	}

	public List<Map<String, Object>> queryAllRoleUser(Map map) {
		return this.mapper.queryAllRoleUser(map);
	}
}
