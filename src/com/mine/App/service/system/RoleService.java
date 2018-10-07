package com.mine.App.service.system;

import com.mine.App.common.pagenation.Pagination;
import com.mine.App.common.util.Constant;
import com.mine.App.dao.system.RoleDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleService implements Constant{
	private Logger logger=Logger.getLogger(RoleService.class);
	
	@Autowired
	private RoleDao roleDao;
	
	public Integer queryRoleListCount(Map params) {
		return this.roleDao.queryOrgListCount(params);
	}

	public List<Map> queryRoleListPagenation(Pagination pagination,Map params) {
		return this.roleDao.queryOrgListPagenation(pagination,params);
	}

	public String addRole(Map params){
		return this.roleDao.addRole(params);
	}

	public Map qryRoleInfoById(Map params) {
		return this.roleDao.qryRoleInfoById(params);
	}

    public void editRole(Map params) {
        this.roleDao.editRole(params);
    }

	public Map delRoleById(Map params) {
		return this.roleDao.delRoleById(params);
	}

	public List<Map<String, Object>> qryUserOfNoRole(Map params) {
		return this.roleDao.qryUserOfNoRole(params);
	}

    public void saveUserRoleInfo(Map params) {
        this.roleDao.saveUserRoleInfo(params);
    }

	public Map<String, Object> qryRoleInfoByOrgId(Map params) {
		return this.roleDao.qryRoleInfoByOrgId(params);
	}

	public Map chkRoleCode(Map params) {
		return this.roleDao.chkRoleCode(params);
	}

	public List<Map<String, Object>> qryRoleInfoByUserId(String userId) {
		return this.roleDao.qryRoleInfoByUserId(userId);
	}

	public int deleteGroupRoleById(Map<Object, Object> params) {
		return this.roleDao.deleteGroupRoleById(params);
	}

	public int updateRoleOrgId(Map map) {
		return this.roleDao.updateRoleOrgId(map);
	}

	public List<Map<String,Object>> queryAllRole(Map map) {
		return this.roleDao.queryAllRole(map);
	}

	public List<Map<String, Object>> queryAllRoleUser(Map map) {
		return this.roleDao.queryAllRoleUser(map);
	}
}
